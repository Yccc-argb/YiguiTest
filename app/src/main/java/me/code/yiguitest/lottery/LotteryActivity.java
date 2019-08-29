package me.code.yiguitest.lottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.code.yiguitest.LogUtils;
import me.code.yiguitest.R;
import me.code.yiguitest.SerialPortUtil;

/**
 * 彩票机
 */
public class LotteryActivity extends AppCompatActivity implements View.OnClickListener, SerialHelperLottery.ReceiveDataListener {

    private Button btnruzhikou;
    private Button btnchupiaokou;
    private Button btnqiedao;
    private Button btnkazhi;
    private Button btnchupiao;
    private Button btndianzisuo;

    private long clickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        LogUtils.init(this);
        initView();
    }

    private void initView() {
        //入纸口
        btnruzhikou = (Button) findViewById(R.id.btn_ruzhikou);
        //出票口
        btnchupiaokou = (Button) findViewById(R.id.btn_chupiaokou);
        //切刀
        btnqiedao = (Button) findViewById(R.id.btn_qiedao);
        //卡纸
        btnkazhi = (Button) findViewById(R.id.btn_kazhi);
        //出票
        btnchupiao = (Button) findViewById(R.id.btn_chupiao);
        btndianzisuo = (Button) findViewById(R.id.btn_dianzisuo);

        btnruzhikou.setOnClickListener(this);
        btnchupiaokou.setOnClickListener(this);
        btnqiedao.setOnClickListener(this);
        btnkazhi.setOnClickListener(this);
        btnchupiao.setOnClickListener(this);
        btndianzisuo.setOnClickListener(this);

        LotteryUtil.serialHelperLottery.setReceiveDataListener(this);

    }

    @Override
    public void onClick(View v) {

        if (System.currentTimeMillis() - clickTime < 1000) {
            Toast.makeText(this, "操作太快", Toast.LENGTH_SHORT).show();
            return;
        }
        clickTime = System.currentTimeMillis();
        if (v == btnruzhikou) {
            //入纸口
            try {
                int[] data = {0x1F, 0x0F, 0x00, 0x01, 0x01, 0x00, 0xAC, 0xA1};
                LotteryUtil.sendCommand(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == btnchupiaokou) {
            //出票口
            try {
//                byte[] chupiaokou = LotteryUtil.queryLotteryMachineState(2);
//                                    1F 0F 00 01 02 00 A6 A1
                int[] data = {0x1F, 0x0F, 0x00, 0x01, 0x02, 0x00, 0xA6, 0xA1};
                LotteryUtil.sendCommand(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == btnqiedao) {
            //切刀
            try {
//                byte[] qiedao = LotteryUtil.queryLotteryMachineState(3);
//                                    1F 0F 00 01 03 00 20 A2
                int[] data = {0x1F, 0x0F, 0x00, 0x01, 0x03, 0x00, 0x20, 0xA2};
                LotteryUtil.sendCommand(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == btnkazhi) {
            //卡纸
            try {
//                byte[] kazhi = LotteryUtil.queryLotteryMachineState(4);
//                                    1F 0F 00 01 04 00 B2 A1
                int[] data = {0x1F, 0x0F, 0x00, 0x01, 0x04, 0x00, 0xB2, 0xA1};
                LotteryUtil.sendCommand(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == btnchupiao) {
            //出彩票
            try {
//                byte[] chupiao = LotteryUtil.exportLottery(1,4);
//                            1F 0F 00 04 01 02 02 00 66 7F
                int[] data = {0x1F, 0x0F, 0x00, 0x04, 0x01, 0x02, 0x02, 0x00, 0x66, 0x7F};
                LotteryUtil.sendCommand(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (v == btndianzisuo) {
            //出彩票
            try {
                SerialPortUtil.ACKResponse(1);
                SerialPortUtil.exportGoods(1,0);
                SerialPortUtil.ACKResponse(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 接收串口数据
     *
     * @param data
     */
    @Override
    public void onDataReceive(final byte[] data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data.length > 8) {
                    if (data[3] == 1) {
                        //代表查询设备状态指令
                        if (data[4] == 1) {
                            //入纸口
                            if (data[6] == 0) {
                                //入纸口有票
                                Toast.makeText(LotteryActivity.this, "入纸口有票", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LotteryActivity.this, "入纸口无票", Toast.LENGTH_SHORT).show();
                            }
                        } else if (data[4] == 2) {
                            //出纸口
                            if (data[6] == 0) {
                                //入纸口有票
                                Toast.makeText(LotteryActivity.this, "出纸口无票", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LotteryActivity.this, "出纸口有票未被取走", Toast.LENGTH_SHORT).show();
                            }

                        } else if (data[4] == 3) {
                            //切刀
                            if (data[6] == 0) {
                                //入纸口有票
                                Toast.makeText(LotteryActivity.this, "切刀位置正常", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LotteryActivity.this, "切刀位置出错", Toast.LENGTH_SHORT).show();
                            }

                        } else if (data[4] == 4) {
                            //卡纸状态
                            if (data[6] == 0) {
                                //入纸口有票
                                Toast.makeText(LotteryActivity.this, "无卡纸情况", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LotteryActivity.this, "处于卡纸状态", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else if (data[3] == 4) {
                        //出票状态
                        if (data[6] == 0) {
                            Toast.makeText(LotteryActivity.this, "出票成功", Toast.LENGTH_SHORT).show();
                        } else if (data[6] == 1) {
                            Toast.makeText(LotteryActivity.this, "出票失败: 无票", Toast.LENGTH_SHORT).show();
                        } else if (data[6] == 2) {
                            Toast.makeText(LotteryActivity.this, "出票失败: 出纸口有票未被取走", Toast.LENGTH_SHORT).show();
                        } else if (data[6] == 3) {
                            Toast.makeText(LotteryActivity.this, "出票失败: 切刀出现故障", Toast.LENGTH_SHORT).show();
                        } else if (data[6] == 4) {
                            Toast.makeText(LotteryActivity.this, "出票失败: 发生卡票状态", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
}
