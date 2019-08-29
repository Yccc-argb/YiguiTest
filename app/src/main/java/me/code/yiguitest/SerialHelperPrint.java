package me.code.yiguitest;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;


/**
 * Created by Yangyanyan on 2018/7/23 0023.
 *
 */

public class SerialHelperPrint {
    private final String pathS1 = "/dev/ttyS0";
    private int rate = 9600;
    private InputStream inputStream;
    private OutputStream outputStream;
    private volatile SerialPort mSerialPort;
    private volatile boolean _isOpen = false;
    private String TAG = "SerialHelper";

    public void open() throws SecurityException, IOException, InvalidParameterException {
        if (!_isOpen) {
            mSerialPort = new SerialPort(new File(pathS1), rate, 0x00);
            inputStream = mSerialPort.getInputStream();
            outputStream = mSerialPort.getOutputStream();
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
                LogUtils.write("串口关闭");
                Log.d("close", "---串口关闭---");
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    Log.d("close", "---outputstream关闭---");
                } catch (IOException e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                    Log.d("close", "---inputstream关闭---");
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
    public void write(byte[] arr) {
        if (outputStream == null) {
            outputStream = mSerialPort.getOutputStream();
        }
        try {
            printSendCommand(arr);
            outputStream.write(arr);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            this.restart();
        }
    }

    /**
     * 向机器写数据
     *
     * @param arr
     */
    public void write2(byte[] arr) {
        if (outputStream == null) {
            outputStream = mSerialPort.getOutputStream();
        }
        try {
            printSendCommand(arr);
            for (int item : arr) {
                outputStream.write(item);
            }
            outputStream.flush();
            Thread.sleep(1000);
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
    private void printSendCommand(byte[] arr) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int item : arr) {
            String format = String.format("%X ", item);
            sb.append(format);
        }
//        Log.i(TAG, "发送: " + sb.toString());
        LogUtils.write("发送: " + sb.toString());
    }

    /**
     * 打印接收字节
     *
     * @param arr
     */
    private void printRevCommand(int[] arr) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int item : arr) {
            String format = String.format("%X ", item);
            sb.append(format);
        }
        LogUtils.write("接收: " + sb.toString());
    }

    public int read(byte[] buffer) throws Exception {
        if (inputStream == null) {
            inputStream = mSerialPort.getInputStream();
        }
        return inputStream == null ? -1 : (buffer != null && buffer.length != 0 ? inputStream.read(buffer) : -2);

    }

}
