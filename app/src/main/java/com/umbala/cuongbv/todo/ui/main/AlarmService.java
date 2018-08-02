package com.umbala.cuongbv.todo.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AlarmService extends Service {

    long reminder;
    int id;
    String name;
    String content;


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

        if (intent != null) {
            Log.i("Turn on service", startId + "");
            reminder = intent.getLongExtra("Task Reminder",0);
            id = intent.getIntExtra("Task ID", 0);
            name = intent.getStringExtra("Task Name");
            content = intent.getStringExtra("Task Content");

            if (!intent.getBooleanExtra("Turn Off", true)) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Log.i("Turn off", reminder + "");

                Intent reminderIntent = new Intent(this, AlarmReceiver.class);
                reminderIntent.putExtra("Task ID", id);
                reminderIntent.putExtra("Turn Off", false);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
            }

            if (reminder != 0) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Log.i("Turn on", reminder + "");

                Intent reminderIntent = new Intent(this, AlarmReceiver.class);
                reminderIntent.putExtra("Task Reminder", 0);
                reminderIntent.putExtra("Task ID", 0);
                reminderIntent.putExtra("Task Name", name);
                reminderIntent.putExtra("Task Content", content);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, reminder, pendingIntent);
            }
        }

        return START_STICKY; //_COMPATIBILITY;
    }
}
