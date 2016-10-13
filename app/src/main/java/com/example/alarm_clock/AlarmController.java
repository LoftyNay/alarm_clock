package com.example.alarm_clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.alarm_clock.db.Db;

/**
 * Created by artem on 12.10.16.
 */
public class AlarmController {

    private static Context context;

    public static void init(Context c) {
        context = c;
    }

    public static void setAlarm(String description, int id, long time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BrcReceiver.class);
        intent.putExtra(Db.DESCRIPTION, description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static void cancelAlarm(int id) {
        Intent intent = new Intent(context, BrcReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
