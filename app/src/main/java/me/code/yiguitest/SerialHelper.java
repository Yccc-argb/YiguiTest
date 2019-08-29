package me.code.yiguitest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;


/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class SerialHelper {
    //    private final String path = "/dev/ttyS3";   //格子机
//    private final String path = "/dev/ttyVC0";   //浩聚
//    private int rate = 9600;
    private final String path = "/dev/ttyS2";  //综合机
    private int rate = 9600;
    private InputStream inputStream;
    private OutputStream outputStream;
    private volatile SerialPort mSerialPort;
    private volatile boolean _isOpen = false;
    private String TAG = "SerialHelper";

    public void open() throws SecurityException, IOException, InvalidParameterException {
        if (!_isOpen) {
            mSerialPort = new SerialPort(new File(path), rate, 0x00);
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
            for (int item : arr) {
                outputStream.write(item);
            }
            outputStream.flush();
            Thread.sleep(500);
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
    public void write2(int[] arr) {
        if (outputStream == null) {
            outputStream = mSerialPort.getOutputStream();
        }
        try {
            printSendCommand(arr);
            for (int item : arr) {
                outputStream.write(item);
            }
            outputStream.flush();
            Thread.sleep(2000);
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
    private void printRevCommand(int[] arr) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int item : arr) {
            String format = String.format("%X ", item);
            sb.append(format);
        }
        LogUtils.write("接收: " + sb.toString());
//        Log.i(TAG, "接收: " + sb.toString());

    }

    public int[] read() {
        if (inputStream == null) {
            inputStream = mSerialPort.getInputStream();
        }
        try {
            int available = inputStream.available();
            if (available > 0) {
                int[] bytes = new int[available];
                for (int i = 0; i < available; i++) {
                    int read = inputStream.read();
                    bytes[i] = read;
                }
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

}
