package me.code.yiguitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;
import static android.view.View.Y;
import static me.code.yiguitest.LogUtils.linkedList;

/**
 * Created by Yangyanyan on 2018/1/27.
 */

public class DateChangeBroadcastReceiver extends BroadcastReceiver {
    private final String ACTION = "Intent.ACTION_DATE_CHANGED";
    private String TAG = "DateChange";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "收到时间变更广播,广播类型: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        LogUtils.write("收到时间变更广播,广播类型: " + intent.getAction());
        //日期变更,用于清志
        if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
            LogUtils.write("收到日期变更广播");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file;
                    Date date = new Date();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        file = new File(context.getExternalFilesDir("Log").getPath() + "/");
                    } else {
                        file = new File(context.getFilesDir().getPath() + "/Log/");
                    }
                    if (!file.exists()) {
                        Log.i(TAG, "日志文件夹不存在");
                        file.mkdir();
                    } else {
                        Log.i(TAG, "日志文件夹存在");
                        if (file.isDirectory()) {
                            //获取所有的日志文件
                            File[] files = file.listFiles();
                            if (files != null && files.length >= 13) {
                                Date oldDate = DateTimeUtil.getDateBeforeOrAfter(date, -7);
                                for (File item : files) {
                                    long l = item.lastModified();
                                    if (l < oldDate.getTime()) {
                                        item.delete();
                                    }
                                }
                            }else {
                                Log.i(TAG, "日志文件夹小于3");
                            }
                        }
                    }

                    String yyyyMMdd = DateTimeUtil.getFormatDateTime(date, "yyyyMMdd");
                    //往前推送7天
                    LogUtils.createNewLogFile(yyyyMMdd);

                }
            }).start();
        }
    }

}
