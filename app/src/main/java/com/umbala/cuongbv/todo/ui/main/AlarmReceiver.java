package com.umbala.cuongbv.todo.ui.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.umbala.cuongbv.todo.R;

public class AlarmReceiver extends BroadcastReceiver {

    static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (!intent.getBooleanExtra("Turn Off", true)) {
            context.stopService(new Intent(context, AlarmReceiver.class));
            mediaPlayer.stop();
        }

        if (action != null && action.equals("Stop") && mediaPlayer != null) {
            context.stopService(new Intent(context, AlarmReceiver.class));
            mediaPlayer.stop();
        } else {
            mediaPlayer = MediaPlayer.create(context, R.raw.reminder);
            mediaPlayer.start();

            Intent stopIntent = new Intent(context, AlarmReceiver.class);
            stopIntent.setAction("Stop");
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText("Reminder")
                    .setOngoing(true)
                    .setContentText("Turn off this Alarm?")
                    .setContentIntent(PendingIntent.getBroadcast(context, intent.getIntExtra("Task ID", 0), stopIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(intent.getIntExtra("Task ID", 0), notification);
        }

        Log.i("receiver", "received " + action);

    }
}
