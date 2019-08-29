package me.code.yiguitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static me.code.yiguitest.R.layout.item;

public class MainActivity extends AppCompatActivity {


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
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_elec_open)
    Button btElecOpen;
    @BindView(R.id.bt_elec_close)
    Button btElecClose;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.checkbox2)
    CheckBox checkbox2;
    @BindView(R.id.checkbox3)
    CheckBox checkbox3;
    @BindView(R.id.checkbox4)
    CheckBox checkbox4;
    private List<String> dataList = new ArrayList<>();
    private MyAdapter myAdapter;
    private String TAG = "MainActivity";
    private IntentFilter intentFilter;
    private DateChangeBroadcastReceiver timeChangeReceiver;
    private int flag;
    private int idLocation = 1;
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
                    tvStatus.setText("光幕检测中...");
                    break;
                case 12:
                    tvStatus.setText("POLL指令接收异常");
                    break;
                case 13:
                    tvStatus.setText("设备当前处于非空闲状态");
                    break;
                case 14:
                    tvStatus.setText("启动电机指令异常");
                    break;
                case 15:
                    tvStatus.setText("电动机启动失败");
                    break;
                case 16:
                    tvStatus.setText("查询出货过程异常");
                    break;

            }

            if (msg.what == 1 || msg.what == 7 || msg.what == 8 || msg.what == 9 || msg.what == 10 ||
                    msg.what == 15 || msg.what == 13)
                if (dialog != null)
                    dialog.dismiss();

        }
    };
    private ProgressDialog dialog;
    private long clickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LogUtils.init(this);
        initGuangMu();
        initData();
        initRecycleView();
//        initDateBroadcastReceiver();
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor config = getSharedPreferences("config", MODE_PRIVATE).edit();
                config.putBoolean("guangmu", b);
                config.commit();
            }
        });
        ((Button) findViewById(R.id.bt_tem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SerialHelperS1 serialHelperS1 = new SerialHelperS1();
                        try {
                            if (!serialHelperS1.isOPen()) {
                                serialHelperS1.open();
                            }
                            int[] arr = {0x01, 0x04, 0x00, 0x00, 0x00, 0x02, 0x71, 0xCB};
                            int[] write = serialHelperS1.write(arr);
                            if (write != null) {
                                //获取温湿度,高位在前,低位在后
                                if (write.length > 6) {
                                    final Integer tem = Integer.valueOf(String.format("%X", write[3]) + String.format("%X", write[4]), 16);
                                    LogUtils.write("tem: " + String.format("%X", write[3]) + String.format("%X", write[4]));
                                    final Integer hem = Integer.valueOf(String.format("%X", write[5]) + String.format("%X", write[6]), 16);
                                    LogUtils.write("hem: " + String.format("%X", write[5]) + String.format("%X", write[6]));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvStatus.setText("温度:" + tem / 10f + "℃ | " + "湿度: " + hem / 10f + "%RH");
                                        }
                                    });

                                }
                            }

                        } catch (Exception e) {

                        } finally {
                            serialHelperS1.close();
                            serialHelperS1 = null;
                        }
                    }
                }).start();

            }
        });

        findViewById(R.id.bt_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (String item : dataList) {
                            try {
                                SerialPortUtil.ACKResponse(idLocation);
                                int[] arrState = SerialPortUtil.checkDeviceState(idLocation);
                                if (arrState != null && arrState.length > 3) {
                                    if (arrState[2] == 0x00) {
//                                        LogUtils.write("设备处于空闲状态");
                                        handler.sendEmptyMessage(2);
//                                        Thread.sleep(1000);
                                        Log.i(TAG, "设备处于空闲状态");
                                        //发送启动出货指令
                                        int[] ints = SerialPortUtil.exportGoods(idLocation, Integer.valueOf(item));
                                        if (ints != null && ints.length > 3) {
                                            if (ints[2] == 0x00 || ints[2] == 0x03) {
                                                handler.sendEmptyMessage(3);
//                                                Thread.sleep(1000);
                                                // TODO: 2018/1/16 发送poll指令查询出货状态
                                                int count = 0;
                                                while (count < 20) {

                                                    int[] ints1 = SerialPortUtil.checkDeviceState(idLocation);

                                                    if (ints1 != null && ints1.length > 11) {
                                                        handler.sendEmptyMessage(4);
                                                        if (ints1[2] == 0x02) {
//                                                            LogUtils.write("电机转动成功");
//                                                            String type = str.startsWith("6") ? "6" : "1";
//                                                            judgeLightState("1");
//                                                            Thread.sleep(300);
                                                            if (ints1[4] == 0x00) {
                                                                //成功
                                                                handler.sendEmptyMessage(7);
                                                            } else {
                                                                flag = ints1[4];
                                                                handler.sendEmptyMessage(8);
                                                            }
                                                            Thread.sleep(500);
                                                            break;

                                                        }
                                                    }
                                                    count++;
                                                }
                                                SerialPortUtil.ACKResponse(idLocation);
                                            } else {
                                                Log.i(TAG, "电动机启动失败");
                                                handler.sendEmptyMessage(15);
                                            }
                                        } else {
                                            Log.i(TAG, "启动电机指令异常");
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
                        }
                    }
                }).start();
            }
        });


        initiCheckBox();
    }

    private void initiCheckBox() {
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

    private void initGuangMu() {
        boolean aBoolean = getSharedPreferences("config", MODE_PRIVATE).getBoolean("guangmu", false);
        checkbox.setChecked(aBoolean);
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
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 10);
        rl.setNestedScrollingEnabled(false);
        rl.setLayoutManager(gridLayoutManager);
        myAdapter = new MyAdapter(item, dataList);
        rl.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - clickTime < 2 * 1000) {
                    Toast toast = Toast.makeText(MainActivity.this, "操作过快", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                clickTime = currentTimeMillis;
//                if (!isShopAppUnInstall()) {
//                    Toast.makeText(MainActivity.this, "请先安装售卖软件", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (dialog == null) {
                    dialog = new ProgressDialog(MainActivity.this);
                }
                dialog.setCancelable(false);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str = dataList.get(position);
                        LogUtils.write("****************************************");
                        LogUtils.write("测试电机指令:" + position);
                        try {
                            SerialPortUtil.ACKResponse(idLocation);
                            int[] arrState = SerialPortUtil.checkDeviceState(idLocation);
                            if (arrState != null && arrState.length > 3) {
                                if (arrState[2] == 0x00) {
                                    handler.sendEmptyMessage(2);
                                    //发送启动电机指令
                                    int[] ints = SerialPortUtil.exportGoods(idLocation, Integer.valueOf(str));
                                    if (ints != null && ints.length > 3) {
                                        if (ints[2] == 0x00 || ints[2] == 0x03) {
                                            handler.sendEmptyMessage(3);
                                            int count = 0;
                                            while (count < 20) {
                                                int[] ints1 = SerialPortUtil.checkDeviceState(idLocation);
                                                if (ints1 != null && ints1.length > 11) {
                                                    handler.sendEmptyMessage(4);
                                                    if (ints1[2] == 0x02) {
                                                        String type = str.startsWith("6") ? "6" : "1";
                                                        judgeLightState(type);
                                                        break;
                                                    }
                                                }
                                                count++;
                                            }
                                        } else {
                                            LogUtils.write("电机启动失败");
                                            handler.sendEmptyMessage(15);
                                        }
                                    } else {
                                        LogUtils.write("启动电机指令异常");
                                        handler.sendEmptyMessage(14);
                                    }
                                } else {
                                    LogUtils.write("设备当前处于非空闲状态");
                                    handler.sendEmptyMessage(13);
                                }

                            } else {
                                LogUtils.write("查询状态指令异常");
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
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 10; j++) {
                String item = i + "" + j;
                Log.i(TAG, "货道号:" + i + "" + j);
                dataList.add(item);
            }

        }

    }


    @OnClick({R.id.bt_id, R.id.bt_elec_open, R.id.bt_elec_close, R.id.bt_fugui})
    public void onClick(View view) {
//        if (!isShopAppUnInstall()) {
//            Toast.makeText(MainActivity.this, "请先安装售卖软件", Toast.LENGTH_SHORT).show();
//            return;
//        }
        switch (view.getId()) {
            case R.id.bt_id:
                try {
                    int[] arr = SerialPortUtil.checkDeviceId(idLocation);
                    if (arr != null && arr.length > 6) {
                        String mbVsersion = arr[2] + "-" + arr[3] + "-" + arr[4] + "-" + arr[5];
                        tvStatus.setText("下位机ID: " + mbVsersion);
                    } else {
                        tvStatus.setText("未读取到下位机反馈的数据");
//                        Log.i(TAG, "查询id为空");
                    }
                } catch (Exception e) {
//                    Log.i(TAG, "查询id异常");
                    tvStatus.setText("读取数据时发生异常");
                }
                break;
//            case R.id.bt_state:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 50; i++) {
//                            try {
//                                int[] arrState = SerialPortUtil.checkDeviceState();
//                                if (arrState != null && arrState.length > 3) {
//                                    if (arrState[2] == 0x00) {
////                                        LogUtils.write("设备处于空闲状态");
//                                        Log.i(TAG, "设备处于空闲状态");
//                                        //发送启动出货指令
//                                        int[] ints = SerialPortUtil.exportGoods(00);
//                                        if (ints != null && ints.length > 3) {
//                                            if (ints[2] == 0x00) {
//                                                // TODO: 2018/1/16 发送poll指令查询出货状态
//                                                int count = 0;
//                                                while (count < 150) {
//                                                    int[] ints1 = SerialPortUtil.checkDeviceState();
//                                                    if (ints1 != null && ints1.length > 11) {
//                                                        if (ints1[2] == 0x02) {
////                                                            LogUtils.write("电机转动成功");
//                                                            Log.i(TAG, "电机转动成功");
//                                                            //ACK应答
//                                                            judgeLightState("1");
//                                                            Thread.sleep(1000);
//                                                            break;
//
//
//                                                        }
//                                                    }
//                                                    count++;
//                                                }
//                                            } else {
//                                                Log.i(TAG, "电动机启动失败");
//                                            }
//                                        } else {
//                                            Log.i(TAG, "出货指令异常");
//                                        }
//                                    } else {
//                                        Log.i(TAG, "设备当前处于非空闲状态");
//                                    }
//
//                                } else {
//                                    Log.i(TAG, "查询状态指令异常");
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                }).start();
//
//                break;
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
            case R.id.bt_fugui:
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                break;
        }
    }

    private void judgeLightState(String type) throws Exception {
        int count = 0;
        int[] ints2 = null;
        while (count < 10) {
            ints2 = SerialPortUtil.checkDeviceState(idLocation);
            if (TextUtils.equals("6", type)) {
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
            } else {
                Log.i(TAG, "弹簧机");
                if (ints2 != null && ints2.length > 11) {
                    if (ints2[4] == 0x00) {
                        if (!(checkbox.isChecked())) {
                            handler.sendEmptyMessage(7);
                            break;
                        } else {
                            if (ints2[11] == 0x00) {
                                handler.sendEmptyMessage(9);
                                LogUtils.write("光幕检测到掉货");
                                break;
                            } else if (ints2[11] == 0x01) {
                                handler.sendEmptyMessage(10);
                                LogUtils.write("光幕启动失败");
                                return;
                            } else if (ints2[11] == 0x02) {
                                if (count == 9) {
                                    SerialPortUtil.ACKResponse(idLocation);
                                    handler.sendEmptyMessage(5);
                                    LogUtils.write("光幕在规定时间内未检测到掉货");
                                    break;
                                }
                                handler.sendEmptyMessage(11);

                            }
                        }

                    }

                }
            }
            count++;
        }
        Log.i(TAG, "count: " + count);
        SerialPortUtil.ACKResponse(idLocation);
        if (count >= 9) {
            if (ints2 != null) {
                flag = ints2[4];
                handler.sendEmptyMessage(8);
                LogUtils.write("电机转动异常#/#" + flag);
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
}
