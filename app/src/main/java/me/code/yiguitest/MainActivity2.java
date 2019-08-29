package me.code.yiguitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity2 extends AppCompatActivity {


    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.bt_id)
    Button btId;
    //    @BindView(R.id.bt_state)
//    Button btState;
    @BindView(R.id.rl)
    RecyclerView rl;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.checkbox2)
    CheckBox checkbox2;
    @BindView(R.id.checkbox3)
    CheckBox checkbox3;
    @BindView(R.id.checkbox4)
    CheckBox checkbox4;
    @BindView(R.id.bt_all)
    Button btAll;
    private List<String> dataList = new ArrayList<>();
    private MyAdapter myAdapter;
    private String TAG = "MainActivity2";
    private IntentFilter intentFilter;
    private DateChangeBroadcastReceiver timeChangeReceiver;
    private int flag;
    private int idLocation = 1;
    private ProgressDialog dialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    tvStatus.setText("查询状态指令异常");
                    break;
                case 2:
                    tvStatus.setText("设备处于空闲状态");
                    break;
                case 3:
                    tvStatus.setText("马达启动成功");
                    break;
                case 4:
                    tvStatus.setText("出货中(电机转动中)...");
                    break;
                case 5:
                    tvStatus.setText("光幕在规定时间未检测到掉货" + "\n出货失败");
                    break;
                case 6:
                    tvStatus.setText("电机转动成功!" + "\n检测电机判断位..." + "\n光幕检测掉货...");
                    break;
                case 7:
                    tvStatus.setText("出货成功");
                    break;
                case 8:
                    tvStatus.setText("电机判断位异常,异常码: " + flag);
                    break;
                case 9:
                    tvStatus.setText("光幕检测到掉货" + "\n出货成功");
                    break;
                case 10:
                    tvStatus.setText("光幕启动失败" + "\n出货失败");
                    break;
                case 11:
                    tvStatus.setText("光幕暂未检测到掉货");
                    break;
                case 12:
                    tvStatus.setText("POLL指令接收异常");
                    break;
                case 13:
                    tvStatus.setText("设备当前处于非空闲状态");
                    break;
                case 14:
                    tvStatus.setText("出货指令异常");
                    break;
                case 15:
                    tvStatus.setText("电动机启动失败");
                    break;
                case 16:
                    tvStatus.setText("查询出货过程异常");
                    break;
                case 17:
                    tvStatus.setText("开锁完成");
                    btAll.setEnabled(true);
                    break;
            }

            if (msg.what == 1 || msg.what == 7 || msg.what == 8 || msg.what == 9 || msg.what == 10 ||
                    msg.what == 15 || msg.what == 13 ||msg.what == 14||msg.what == 12 ||msg.what == 16)
                if (dialog != null)
                    dialog.dismiss();
        }
    };
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        LogUtils.init(this);
        initData();
        initRecycleView();
//        initDateBroadcastReceiver();
        checkbox1.setChecked(true); //默认板卡地址为1
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idLocation = 1;
                    checkbox2.setChecked(false);
                    checkbox3.setChecked(false);
                    checkbox4.setChecked(false);
                }

            }
        });
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idLocation = 2;
                    checkbox1.setChecked(false);
                    checkbox3.setChecked(false);
                    checkbox4.setChecked(false);
                }
            }
        });
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idLocation = 3;
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(false);
                    checkbox4.setChecked(false);
                }
            }
        });
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idLocation = 4;
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(false);
                    checkbox3.setChecked(false);
                }
            }
        });
    }


    private void initDateBroadcastReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        timeChangeReceiver = new DateChangeBroadcastReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(timeChangeReceiver);
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 10);
        rl.setNestedScrollingEnabled(false);
        rl.setLayoutManager(gridLayoutManager);
        myAdapter = new MyAdapter(R.layout.item, dataList);
        rl.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
//                if (!isShopAppUnInstall()){
//                    Toast.makeText(MainActivity2.this, "请先安装售卖软件", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (dialog == null) {
                    dialog = new ProgressDialog(MainActivity2.this);
                }
                dialog.setCancelable(false);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str = dataList.get(position);
//                        for (int i = 0; i < 1; i++) {
                        try {
                            SerialPortUtil.ACKResponse(idLocation);
                            int[] arrState = SerialPortUtil.checkDeviceState(idLocation);
                            if (arrState != null && arrState.length > 3) {
                                if (arrState[2] == 0x00) {
//                                        LogUtils.write("设备处于空闲状态");
                                    handler.sendEmptyMessage(2);
                                    Thread.sleep(1000);
                                    Log.i(TAG, "设备处于空闲状态");
                                    //发送启动出货指令
                                    int[] ints = SerialPortUtil.exportGoods(idLocation, Integer.valueOf(str) - 1);
                                    if (ints != null && ints.length > 3) {
                                        if (ints[2] == 0x00 || ints[2] == 0x03) {
                                            handler.sendEmptyMessage(3);
                                            Thread.sleep(1000);
                                            // TODO: 2018/1/16 发送poll指令查询出货状态
                                            int count = 0;
                                            while (count < 120) {

                                                int[] ints1 = SerialPortUtil.checkDeviceState(idLocation);

                                                if (ints1 != null && ints1.length > 11) {
                                                    handler.sendEmptyMessage(4);
                                                    if (ints1[2] == 0x02) {
//                                                            LogUtils.write("电机转动成功");
                                                        Log.i(TAG, "电机转动成功");
                                                        judgeLightState();
//                                                            Thread.sleep(1000);
                                                        break;

                                                    }
                                                }
                                                count++;
                                            }
                                        } else {
                                            Log.i(TAG, "电动机启动失败");
                                            handler.sendEmptyMessage(15);
                                        }
                                    } else {
                                        Log.i(TAG, "出货指令异常");
                                        handler.sendEmptyMessage(14);
                                    }
                                } else {
                                    Log.i(TAG, "设备当前处于非空闲状态");
                                    handler.sendEmptyMessage(13);
                                }

                            } else {
                                Log.i(TAG, "查询状态指令异常");
                                handler.sendEmptyMessage(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        }

                    }

                }).start();


            }
        });
    }

    private void initData() {
        dataList.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 10; j++) {
                String item = "";
                if (j == 10) {
                    item = (i + 1) + "0";
                } else {

                    item = i + "" + j;
                }
                Log.i(TAG, "货道号:" + i + "" + j);
                dataList.add(item);
            }

        }

    }


    @OnClick({R.id.bt_id, R.id.bt_all, R.id.bt_elec_open, R.id.bt_elec_close,R.id.bt_back})
    public void onClick(View view) {
//        if (!isShopAppUnInstall()){
//            Toast.makeText(MainActivity2.this, "请先安装售卖软件", Toast.LENGTH_SHORT).show();
//            return;
//        }
        switch (view.getId()) {
            case R.id.bt_id:
                try {
                    int[] arr = SerialPortUtil.checkDeviceId(idLocation);

                    if (arr != null) {
                        String s = "";
                        for (int i = 0; i < arr.length; i++) {
                            if (i == arr.length - 1) {
                                s = s + String.format("%X", arr[i]);
                            } else {
                                s = s + String.format("%X", arr[i]) + "-";
                            }

                        }
//                        String mbVsersion = arr[2] + "-" + arr[3] + "-" + arr[4] + "-" + arr[5];
                        tvStatus.setText("下位机ID: " + s);
                    } else {
                        tvStatus.setText("未读取到下位机反馈的数据");
//                        Log.i(TAG, "查询id为空");
                    }
                } catch (Exception e) {
//                    Log.i(TAG, "查询id异常");
                    tvStatus.setText("读取数据时发生异常");
                }
                break;
            case R.id.bt_all:
                //一键开锁

                tvStatus.setText("一键开锁中...");
                btAll.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 100; i++) {
                            try {
                                SerialPortUtil.exportGoodsWhitoutRead(idLocation, i - 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        handler.sendEmptyMessage(17);
                    }
                }).start();
                break;
            case R.id.bt_elec_open:
                //继电器开
                try {
                    SerialPortUtil.ACKResponse(idLocation);
                    //0关 1开
                    int[] ints = SerialPortUtil.controlElec2(idLocation, 1);
                    if (ints != null) {
                        if (ints.length > 3) {
                            if (ints[2] == 0) {
                                Toast.makeText(this, "继电器已闭合", Toast.LENGTH_LONG).show();
                            } else if (ints[2] == 1){
                                Toast.makeText(this, "继电器已断开", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "继电器闭合失败", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "继电器闭合失败", Toast.LENGTH_LONG).show();
                    }
                    SerialPortUtil.ACKResponse(idLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SerialPortUtil.serialHelper.close();
                }
                break;
            case R.id.bt_elec_close:
                //继电器关
                try {
                    SerialPortUtil.ACKResponse(idLocation);
                    int[] ints = SerialPortUtil.controlElec2(idLocation, 0);
                    if (ints != null) {
                        if (ints.length > 3) {
                            if (ints[2] == 0) {
                                Toast.makeText(this, "继电器已开启", Toast.LENGTH_LONG).show();
                            } else if (ints[2] == 1){
                                Toast.makeText(this, "继电器已断开", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "继电器断开失败", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "继电器断开失败", Toast.LENGTH_LONG).show();
                    }
                    SerialPortUtil.ACKResponse(idLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SerialPortUtil.serialHelper.close();
                }
                break;

            case R.id.bt_back:
                MainActivity2.this.finish();
                break;
        }
    }

    private void judgeLightState() throws Exception {
        int count = 0;
        int[] ints2 = null;
        while (count < 100) {
            ints2 = SerialPortUtil.checkDeviceState(idLocation);

            if (ints2 != null && ints2.length > 4) {
                Log.i(TAG, "电子锁");

                if (ints2[4] == 0x00) {
                    SerialPortUtil.ACKResponse(idLocation);
                    handler.sendEmptyMessage(7);
                    Log.i(TAG, "出货成功");
                    break;
                } else {
                    Log.i(TAG, "出货中...");
                }

            }

            count++;
        }
        SerialPortUtil.ACKResponse(idLocation);
        if (count >= 100) {
            if (ints2 != null) {
                flag = ints2[4];
                handler.sendEmptyMessage(8);
                Log.i(TAG, "电机转动异常");
            }
        }
    }

    private boolean isShopAppUnInstall() {
        PackageManager packageManager = this.getApplication().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo item : installedPackages) {
            if (item.packageName.equals("it.dongjun.yigui"))
                return true;
        }
        return false;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //停止计时
//                if (countDownTimer != null) {
//                    countDownTimer.cancel();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                //抬起时开始计时
//                timeCountDown();
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    public void timeCountDown() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(5 * 60 * 1000l, 1000l) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    //进入广告页
                    String[] arrayRestart = {"su", "-c", "reboot"};
                    try {
                        Runtime.getRuntime().exec(arrayRestart);
                    } catch (IOException e) {
                        Log.i(TAG, "重启工控机异常");
                    }
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.start();
        }
    }
}
