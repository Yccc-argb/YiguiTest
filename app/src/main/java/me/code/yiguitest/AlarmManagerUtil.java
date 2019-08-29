package me.code.yiguitest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by Yangyanyan on 2018/8/3 0003.
 * 闹钟定时任务
 */

public class AlarmManagerUtil {

    /**
     * 设置闹钟
     * @param action
     * @param id   //多个闹钟需要设置不一样的id
     * @param rebootTime
     * @param clazz
     */
    public static void setAlarm(Context context,String action, int id, int hour,int min, Class clazz) {

        Intent intent = new Intent(context, clazz);
        intent.setAction(action);
        PendingIntent sender = PendingIntent
                .getBroadcast(context, id, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();   //获取系统启动之后到当前时刻经历的时间
        //java.lang.System.currentTimeMillis()，它返回从 UTC 1970 年 1 月 1 日午夜开始经过的毫秒数。
        long systemTime = System.currentTimeMillis();


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));  //设置时区

        calendar.set(Calendar.HOUR_OF_DAY, hour);//设置为凌晨1点
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //选择的定时时间
        long selectTime = calendar.getTimeInMillis();    //计算出设定的时间

        //  如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, hour);
            selectTime = calendar.getTimeInMillis();
        }
        long time = selectTime - systemTime;// 计算现在时间到设定时间的时间差
        long my_Time = firstTime + time;//系统 当前的时间+时间差
        // 进行闹铃注册
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, my_Time, AlarmManager.INTERVAL_DAY, sender);

    }


    /**
     * 取消闹钟
     *
     * @param action
     * @param clazz
     */
    public static void canclaAlarm(Context context,String action, int id, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setAction(action);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }
}
