package com.umbala.cuongbv.todo.ui.edit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.umbala.cuongbv.todo.R;

public class AlarmService extends Service {

    long reminder;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent stopIntent = new Intent(this, AlarmReceiver.class);
        stopIntent.setAction("Stop");
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Reminder")
                .setOngoing(true)
                .setContentText("Turn of this Alarm?")
                .setContentIntent(PendingIntent.getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
        reminder = intent.getLongExtra("Reminder",0);
        Log.i("Reminder", String.valueOf(reminder - System.currentTimeMillis()));

        startForeground(1, notification);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i2 = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i2, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminder, pendingIntent);
        Log.i("service","alarm was set");


        return START_STICKY;
    }
}
