package me.code.yiguitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrinterActivity extends AppCompatActivity {

    @BindView(R.id.btn_init)
    Button btnInit;
    @BindView(R.id.btn_print)
    Button btnPrint;
    @BindView(R.id.btn_status)
    Button btnStatus;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.btn_qie)
    Button btnQie;
    @BindView(R.id.btn_params)
    Button btnParams;
    @BindView(R.id.tv_paper)
    TextView tvPaper;
    @BindView(R.id.tv_work_state)
    TextView tvWorkState;
    @BindView(R.id.tv_recive_cache)
    TextView tvReciveCache;
    @BindView(R.id.tv_get_paper)
    TextView tvGetPaper;
    @BindView(R.id.tv_paper_over)
    TextView tvPaperOver;
    @BindView(R.id.tv_jitou)
    TextView tvJitou;
    String[] content = new String[]{
            "河南省人民医院智慧大药房1楼",
            "如需发票请联系:18683520760",  //虚线
            "产品名称:电子体温计",
            "生产商:广州市金鑫宝电子有限公司",
            "单价:58.8",
            "数量:1",
            "金额:58.8",
            "生产批次号:20180316",
            "产品规格:1瓶",
            "生产日期:2018-03-16",
            "保质日期:2022-03-16",   //虚线
            "订单号:20180820234323114646067",
            "支付总额:58.8",
            "购买时间:2018-08-20 23:43:23",
            "购买地点:河南省—郑州市—金水区",
            "支付方式:微信支付",   //虚线
            "咨询电话:400-8811-086",
            "药品属于特殊商品,一经售出,如无质量问题,概不退换!",


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        ButterKnife.bind(this);
        LogUtils.init(this);
//        AlarmManagerUtil.setAlarm(this,"LOG",1,18,3,LogReceive.class);
//        AlarmManagerUtil.setAlarm(this,"REBOOT",2,18,4,RebootReceive.class);
    }

    @OnClick({R.id.btn_init, R.id.btn_print, R.id.btn_status, R.id.btn_enter, R.id.btn_qie, R.id.btn_params, R.id.btn_xuxian, R.id.btn_duiqi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_init:
                //初始化打印机
                try {
                    PrintUtil.initPrint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_print:
                //打印
                //取消闹钟
                AlarmManagerUtil.canclaAlarm(this,"REBOOT",2,RebootReceive.class);
                try {
                    int state = PrintUtil.queryPrinterState();
                    if ((state & 0x02) != 0x02 && (state & 0x01) == 0x01) {
                        PrintUtil.initPrint();
                        for (int i = 0; i < content.length; i++) {
                            if (i == 0) {
                                PrintUtil.setPrintFont(true, 1, 2);
                            } else if (i == content.length - 1) {
                                PrintUtil.setPrintFont(true, 1, 1);
                            } else {
                                PrintUtil.setPrintFont(false, 1, 1);
                            }

                            PrintUtil.printText(content[i] + "\n");
                            //回车换行
//                        PrintUtil.initPrint();
                            if (i == 1 || i == 10 || i == 15) {
                                //虚线
                                PrintUtil.setisnoLine();
//                            PrintUtil.formfeed(1);
                            }else {
                                PrintUtil.setCarriage();
//                            PrintUtil.formfeed(1);
                            }


                        }
                        for (int i = 0; i < 2; i++) {
                            PrintUtil.setCarriage();
                        }
                        //切刀
                        PrintUtil.cutPaper();
                    }else {
                        Log.i("Print", "打印机状态异常");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_status:
                //打印机状态

                break;
            case R.id.btn_enter:
                //回车换行
                try {
                    PrintUtil.setCarriage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_qie:
                //切刀
                try {
                    PrintUtil.cutPaper();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_params:
                //设置参数
                try {
                    PrintUtil.setPrintFont(true, 2, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_xuxian:
                //打印虚线
                try {
                    PrintUtil.setisnoLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_duiqi:
                //打印虚线
                //设置
//                AlarmManagerUtil.setAlarm(this,"REBOOT",2,18,7,RebootReceive.class);
                try {
//                    PrintUtil.setPosition(1, 1, 4);

                    int len = PrintUtil.queryPrinterState();
                    if ((len & 0x20) == 0x20) {
                        tvJitou.setText("机头状态：机头状态正常");
                    } else {
                        tvJitou.setText("机头状态：机头打开");
                    }
                    if ((len & 0x10) == 0x10) {
                        tvPaperOver.setText("纸将尽检测器：纸将尽传感器有纸");
                    } else {
                        tvPaperOver.setText("纸将尽检测器：纸将尽");
                    }

                    if ((len & 0x08) == 0x08) {
                        tvGetPaper.setText("取纸检测器：取纸传感器有纸");
                    } else {
                        tvGetPaper.setText("取纸检测器：取纸传感器无纸");
                    }
                    if ((len & 0x04) == 0x04) {
                        tvReciveCache.setText("接收缓冲区：打印缓存区满");
                    } else {
                        tvReciveCache.setText("接收缓冲区：打印缓存区未满");
                    }
                    if ((len & 0x02) == 0x02) {
                        tvWorkState.setText("工作状态：打印机正在打印");
                    } else {
                        tvWorkState.setText("工作状态：打印机空闲");
                    }
                    if ((len & 0x01) == 0x01) {
                        tvPaper.setText("纸检测器：打印机有纸");
                    } else {
                        tvPaper.setText("纸检测器：打印机缺纸");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AlarmManagerUtil.canclaAlarm(this,"LOG",1,LogReceive.class);
//        AlarmManagerUtil.canclaAlarm(this,"REBOOT",2,RebootReceive.class);
    }
}
