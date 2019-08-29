package me.code.yiguitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Yangyanyan on 2018/8/13 0013.
 *
 */

public class RebootReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("REBOOT")) {
            Log.i("RebootReceive", "---重启闹钟启动---");
            Toast.makeText(context, "重启闹钟启动", Toast.LENGTH_SHORT).show();
        }
    }
}
