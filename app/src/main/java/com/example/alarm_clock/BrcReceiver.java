package com.example.alarm_clock;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.alarm_clock.db.Db;

import java.util.Calendar;

/**
 * Created by artem on 11.10.16.
 */
public class BrcReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.points)
                        .setContentTitle(context.getResources().getString(R.string.alarm))
                        .setContentText(intent.getStringExtra(Db.DESCRIPTION));

        notificationManager.notify(Calendar.getInstance().get(Calendar.MILLISECOND), builder.build());
    }
}
