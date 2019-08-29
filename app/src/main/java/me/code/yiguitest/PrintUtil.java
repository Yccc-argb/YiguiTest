package me.code.yiguitest;

/**
 * Created by Yangyanyan on 2018/8/8 0008.
 * 打印工具类
 */

public class PrintUtil {

    public static SerialHelperPrint serialHelperPrint = new SerialHelperPrint();


    /**
     * 初始化打印机
     *
     * @throws Exception
     */
    public static void initPrint() throws Exception {

        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(new byte[]{(byte) 27, (byte) 64});

    }

    /**
     * 打印
     *
     * @param text
     * @throws Exception
     */
    public static void printText(String text) throws Exception {
        byte[] gbks = (text).getBytes("gbk");
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(gbks);
    }


    /**
     * 查询打印机状态
     *
     * @return
     * @throws Exception
     */
    public static int queryPrinterState() throws Exception {
        byte[] val = new byte[30];
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        for (int i = 0; i < 3; ++i) {
            serialHelperPrint.write(new byte[]{(byte) 27, (byte) 118});

            Thread.sleep(100L);

            serialHelperPrint.read(val);
        }
        return val[0];
    }

    /**
     * 回车换行
     *
     * @throws Exception
     */
    public static void setCarriage() throws Exception {
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(new byte[]{0x0d, 0x0d});
    }


    /**
     * 切刀
     *
     * @throws Exception
     */
    public static void cutPaper() throws Exception {
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(new byte[]{0x1b, 0x69});
    }

    /**
     * 打印虚线
     *
     * @throws Exception
     */
    public static void setisnoLine() throws Exception {
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        for (int i = 0; i < 32; i++) {
            printText("-");
        }
        byte[] cr = new byte[1];
        cr[0] = (byte) 0x0d;
        serialHelperPrint.write(cr);
    }


    // 进纸n行
    public static  void formfeed(int number) throws Exception {
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(new byte[]{0x1b, 0x64, (byte) number});
    }


    /**
     * 设置位置
     *
     * @param Top
     * @param Light
     * @param roate
     * @throws Exception
     */
    public static void setPosition(int Top, int Light, int roate) throws Exception {
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        if ((byte) Top == 0) {
            //上对齐
            serialHelperPrint.write(new byte[]{0x1c, 0x72, 0});
        } else {
            //下对齐
            serialHelperPrint.write(new byte[]{0x1c, 0x72, 1});
        }

        if ((byte) Light == 1) {
            //左对齐
            serialHelperPrint.write(new byte[]{0x1b, 0x61, 1});
        } else if ((byte) Light == 2) {
            //居中
            serialHelperPrint.write(new byte[]{0x1b, 0x61, 2});
        } else {
            //右对齐
            serialHelperPrint.write(new byte[]{0x1b, 0x61, 0});
        }

        if ((byte) roate == 1) {// 逆时针90°
            serialHelperPrint.write(new byte[]{0x1c, 0x49, 1});
        } else if ((byte) roate == 2) {// 逆时针180°
            serialHelperPrint.write(new byte[]{0x1c, 0x49, 2});
        } else if ((byte) roate == 3) {// 逆时针270°
            serialHelperPrint.write(new byte[]{0x1c, 0x49, 3});
        } else {// 0°
            serialHelperPrint.write(new byte[]{0x1c, 0x49, 0});
        }
    }



    /**
     * 设置字体
     * @param isBlack  是否黑体
     * @param width  倍宽
     * @param height  倍高
     * @throws Exception
     */
    public static void setPrintFont(boolean isBlack, int width, int height) throws Exception {
        //默认字体是汉字,不可设置其他
        if (!serialHelperPrint.isOPen()) {
            serialHelperPrint.open();
        }
        serialHelperPrint.write(new byte[]{0x1C, 0x26});
        if (isBlack) {
            serialHelperPrint.write(new byte[]{0x1b, 0x21, (byte) 0x08});
        } else {
            serialHelperPrint.write(new byte[]{0x1b, 0x21, (byte) 0x00});
        }

        if (width < 0) {
            width = 1;
        }
        if (width > 8) {
            width = 8;
        }
        serialHelperPrint.write(new byte[]{0x1b, 0x55, (byte) width});
        if (height < 0) {
            height = 1;
        }
        if (height > 8) {
            height = 8;
        }
        serialHelperPrint.write(new byte[]{0x1b, 0x56, (byte) height});

    }

}
