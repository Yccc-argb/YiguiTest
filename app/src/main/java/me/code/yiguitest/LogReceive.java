package me.code.yiguitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Yangyanyan on 2018/8/13 0013.
 */

public class LogReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("LOG")) {
            Log.i("LogReceive", "---日志闹钟启动---");
            Toast.makeText(context, "日志闹钟启动", Toast.LENGTH_SHORT).show();
        }
    }
}
