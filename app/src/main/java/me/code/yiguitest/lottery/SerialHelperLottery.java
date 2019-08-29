package me.code.yiguitest.lottery;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import me.code.yiguitest.LogUtils;
import me.code.yiguitest.SerialPort;


/**
 * Created by Yangyanyan on 2018/7/23 0023.
 */

public class SerialHelperLottery {
    private final String path = "/dev/ttyS3";
    private int rate = 9600;
    private InputStream inputStream;
    private OutputStream outputStream;
    private volatile SerialPort mSerialPort;
    private volatile boolean _isOpen = false;
    private String TAG = "SerialHelper";
    private ReadThread readThread;

    public void open() throws SecurityException, IOException, InvalidParameterException {
        if (!_isOpen) {
            mSerialPort = new SerialPort(new File(path), rate, 0x00);
            inputStream = mSerialPort.getInputStream();
            outputStream = mSerialPort.getOutputStream();
            readThread = new ReadThread();
            readThread.start();
            _isOpen = true;
        }
    }

    /**
     * 判断串口状态
     *
     * @return
     */
    public boolean isOPen() {
        return _isOpen && inputStream != null && outputStream != null;
    }

    public void restart() {
        this.close();
        try {
            this.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭串口
     */
    public void close() {
        if (_isOpen) {
            if (mSerialPort != null) {
                mSerialPort.close();
                mSerialPort = null;
                LogUtils.write("---串口关闭---");
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            _isOpen = false;
        }
    }

    /**
     * 向机器写数据
     *
     * @param arr
     */
    public void write(int[] arr) {
        if (outputStream == null) {
            outputStream = mSerialPort.getOutputStream();
        }
        try {
            printSendCommand(arr);
            for (int i = 0; i < arr.length; i++) {

                outputStream.write(arr[i]);
            }
            outputStream.flush();
//            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
            this.restart();
        }
    }



    /**
     * 打印发送字节
     *
     * @param arr
     */
    private void printSendCommand(int[] arr) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int item : arr) {
            String format = String.format("%X ", item);
            sb.append(format);
        }
        LogUtils.write("发送: " + sb.toString());
//        Log.i(TAG, "发送: " + sb.toString());
    }

    /**
     * 打印接收字节
     *
     * @param arr
     */
    private void printRevCommand(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int item : arr) {
            String format = String.format("%X ", item);
            sb.append(format);
        }
        LogUtils.write("接收: " + sb.toString());
//        Log.i(TAG, "接收: " + sb.toString());

    }

    public byte[] read() {
        if (inputStream == null) {
            inputStream = mSerialPort.getInputStream();
        }
        try {
            int available = inputStream.available();
            if (available > 0) {
                byte[] bytes = new byte[available];
                int read = inputStream.read();
                printRevCommand(bytes);
                return bytes;
            } else {
                LogUtils.write("未接收到下位机返回的数据");

            }
        } catch (Exception e) {
            LogUtils.write("读取过程发生异常");
            e.printStackTrace();
        }
        return null;
    }


    private class ReadThread extends Thread {
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                if (inputStream == null)
                    if (mSerialPort != null)
                        inputStream = mSerialPort.getInputStream();

                try {
                    if (inputStream != null) {
                        int available = inputStream.available();
                        if (available > 0) {
                            byte[] bytes = new byte[available];
                            int size = inputStream.read(bytes);
                            if (size > 0 || size != -1) {
                                printRevCommand(bytes);
                                if (receiveDataListener != null) {
                                    receiveDataListener.onDataReceive(bytes);
                                }else {
                                    Log.i(TAG, "receiveDataListener == null");
                                }
                            }
                        } else {
//                        printData("receive", null);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
//                    byte[] bytes = new byte[16];
//                    int size = mInputStream.read(bytes);
//                    if (size > 0 || size != -1){
//                        printData("receive", bytes);
//                        onDataReceive(bytes);
//                    }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "IOException:" + e.getMessage());
                }


            }
        }
    }

    interface  ReceiveDataListener{
        void onDataReceive(byte[] data);
    }

    private ReceiveDataListener receiveDataListener;
    public void setReceiveDataListener(ReceiveDataListener receiveDataListener){
        this.receiveDataListener = receiveDataListener;
    }

}
