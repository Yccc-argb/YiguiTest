package me.code.yiguitest.lottery;

import me.code.yiguitest.CRCCheck16;

/**
 * Created by Yangyanyan on 2019/6/24 0024.
 */

public class LotteryUtil {


    public static SerialHelperLottery serialHelperLottery = new SerialHelperLottery();


//    起始符 设备地址 命令类型 命令参数 数据长度 数据 CRC 校验码
//    2 Bytes 1 Byte 1 Byte 1 Byte 1 Byte n Bytes 2 Bytes
//    0x1F 0x0F Addr CMD Type Length Data0~DataN CRC_H CRC_L

    /**
     * 读取设备相关状态
     * @param type 1入纸口传感器  2出票口  3切刀状态  卡纸状态
     * @return
     */
    public static byte[] queryLotteryMachineState(int type) throws Exception{
        byte[] data = {0x1F,0x0F,0x00,0x01,(byte)type,0x00};
        byte[] completeData = {0x1F,0x0F,0x00,0x01,(byte)type,0x00,getCheckData(data)[1],getCheckData(data)[0]};
        return completeData;
    }


    /**
     * 出彩票
     * @param type  长度类型
     * @param length  长度
     * @return
     * @throws Exception
     */
    public  static byte[] exportLottery(int type,int length) throws Exception{
//        0x1F 0x0F addr 0x04 0x01 0x01 0x04 crc_h crc_l
        byte[] data = {0x1F,0x0F,0x00,0x04,(byte) type,0x01,(byte) length};
        byte[] completeData = {0x1F,0x0F,0x00,0x04,(byte) type,0x01,(byte) length,getCheckData(data)[0],getCheckData(data)[1]};
        return completeData;
    }

    /**
     * 发送指令
     *
     * @param bytes
     * @throws Exception
     */
    public static byte[] sendCommand(int[] bytes) throws Exception {
        if (!serialHelperLottery.isOPen()) {
            //打开串口
            serialHelperLottery.open();
        }
        serialHelperLottery.write(bytes);
//        byte[] revBytes = null;
//        int loopCount = 3;
//        while (loopCount-- > 0) {
//            if (loopCount < 2) {
//                Log.e("Main", "机器无响应,重试");
//            }
//            serialHelperLottery.write(bytes);
////            revBytes = serialHelperLottery.read();
////            if (revBytes != null) {
////                return revBytes;
////            }
//        }
        return null;
    }

    public static byte[] getCheckData(byte[] bytes) {
        int crc16 = CRCCheck16.calcCrc16(bytes);
        String str = Integer.toHexString(crc16);
        int i1 = Integer.parseInt(str.substring(0, 2), 16);
        int i2 = Integer.parseInt(str.substring(2, 4), 16);
        return new byte[]{(byte)i1, (byte)i2};
    }


}
