package com.umbala.cuongbv.todo.ui.edit;

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
        if (action != null && action.equals("Stop") && mediaPlayer != null) {
            context.stopService(new Intent(context, AlarmService.class));
            mediaPlayer.stop();
        } else {
            mediaPlayer = MediaPlayer.create(context, R.raw.reminder);
            mediaPlayer.start();
        }

        Log.i("receiver", "received " + action);

    }
}
