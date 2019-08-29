package me.code.yiguitest;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class SerialPortUtil {
    public static SerialHelper serialHelper = new SerialHelper();


    /**
     * 校验和
     *
     * @param bytes
     * @return
     */
    private byte getFcc(byte[] bytes) {
        int checkSum = 0x00;
        for (int i = 0; i < bytes.length; i++) {
            checkSum += bytes[i];
        }
        checkSum = checkSum % 0xFF;
        return (byte) checkSum;
    }


    /**
     * 发送指令
     *
     * @param bytes
     * @throws Exception
     */
    public static int[] sendCommand(int[] bytes) throws Exception {
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        int[] revBytes = null;
        int loopCount = 3;
        while (loopCount-- > 0) {
//            if (loopCount < 2) {
//                Log.e("Main", "机器无响应,重试");
////                LogUtils.write("机器无响应,重试");
//            }
            serialHelper.write(bytes);
            revBytes = serialHelper.read();
            if (revBytes != null) {
                return revBytes;
            }
        }
        return null;
    }

    /**
     * 查询板卡地址
     *
     * @return
     * @throws Exception
     */
    public static int[] checkDeviceId() throws Exception {
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        //板卡地址为2
        int[] arr = new int[]{0x02, 0x01, 0xC1, 0x10};
        int[] ints = sendCommand(arr);
        serialHelper.close();
        return ints;
    }

    /**
     * 多板卡查询ID
     *
     * @param idLocation 板卡地址
     * @return
     * @throws Exception
     */
    public static int[] checkDeviceId(int idLocation) throws Exception {
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        //板卡地址为2
        byte[] bytes = {(byte) idLocation, 0x01};
        int[] checkData = getCheckData(bytes);
        int[] arr = {idLocation, 0x01, checkData[0], checkData[1]};
        int[] ints = sendCommand(arr);
        serialHelper.close();
        return ints;
    }


    /**
     * 查询当前的设备状态
     *
     * @return
     */
    public static int[] checkDeviceState() throws Exception {
        int[] arr = {0x02, 0x03, 0x40, 0xD1};
        return sendCommand(arr);
    }

    /**
     * 查询当前的设备状态
     *
     * @return
     */
    public static int[] checkDeviceState(int Location) throws Exception {

        byte[] bytes = {(byte) Location, 0x03};
        int[] checkData = getCheckData(bytes);
        int[] arr = {Location, 0x03, checkData[0], checkData[1]};
        return sendCommand(arr);
    }


    public static int[] exportGoods(int channle) throws Exception {
        byte[] bytes = {0x02, 0x05, (byte) channle};
        int crc16 = CRCCheck16.calcCrc16(bytes);
        String str = Integer.toHexString(crc16);
        int i1 = Integer.parseInt(str.substring(0, 2), 16);
        int i2 = Integer.parseInt(str.substring(2, 4), 16);
        int[] ints = {0x02, 0x05, channle, i2, i1};
        return sendCommand(ints);
    }


    public static int[] exportGoods(int location, int channle) throws Exception {
        byte[] bytes = {(byte) location, 0x05, (byte) channle};
        int[] checkData = getCheckData(bytes);
        int[] ints = {location, 0x05, channle, checkData[0], checkData[1]};
        return sendCommand(ints);
    }

    public static int[] ACKResponse() throws Exception {
        int[] arr = {0x02, 0x06, 0x80, 0xD2};
        return sendCommand(arr);
    }

    public static int[] ACKResponse(int Location) throws Exception {
        byte[] arr = {(byte) Location, 0x06};
        int[] checkData = getCheckData(arr);
        int[] arr1 = {Location, 0x06, checkData[0], checkData[1]};
        return sendCommand(arr1);
    }

    public static int[] getCheckData(byte[] bytes) {
        int crc16 = CRCCheck16.calcCrc16(bytes);
        String str = Integer.toHexString(crc16);
        int i1 = Integer.parseInt(str.substring(0, 2), 16);
        int i2 = Integer.parseInt(str.substring(2, 4), 16);
        return new int[]{i2, i1};
    }


    /**
     * 用于格子架机开锁
     *
     * @param mbId
     * @param channle
     * @throws Exception
     */
    public static void exportGoodsWhitoutRead(int mbId, int channle) throws Exception {
        byte[] bytes = {(byte) mbId, 0x05, (byte) channle};
        int[] checkData = getCheckData(bytes);
        int[] ints = {mbId, 0x05, channle, checkData[0], checkData[1]};
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        //先响应ACK
        response(mbId);
        serialHelper.write2(ints);
//        serialHelper.read();
        //再给ACK响应
        response(mbId);


    }

    private static void response(int mbId) {
        byte[] arr = {(byte) mbId, 0x06};
        int[] checkData2 = getCheckData(arr);
        int[] arr1 = {mbId, 0x06, checkData2[0], checkData2[1]};
        serialHelper.write(arr1);
    }

    public static int[] controlElec(int mbId, int type) throws Exception {
        byte[] bytes = {(byte) mbId, 0x05, (byte) type};
        int[] checkData = getCheckData(bytes);
        int[] ints = {mbId, 0x05, type, checkData[0], checkData[1]};
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        return sendCommand(ints);
    }


    /**
     * 吴俊单独板子继电器功能
     * @param mbId
     * @param type
     * @return
     * @throws Exception
     */
    public static int[] controlElec2(int mbId, int type) throws Exception {
        byte[] bytes = {(byte) mbId, 0x07, (byte) type};
        int[] checkData = getCheckData(bytes);
        int[] ints = {mbId, 0x07, type, checkData[0], checkData[1]};
        if (!serialHelper.isOPen()) {
            //打开串口
            serialHelper.open();
        }
        return sendCommand(ints);
    }
}
