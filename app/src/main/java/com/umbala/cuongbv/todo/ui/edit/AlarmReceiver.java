package com.umbala.cuongbv.todo.ui.edit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.umbala.cuongbv.todo.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//
//        Intent myIntent = new Intent(context, Music.class);
//        context.startService(myIntent);



        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.reminder);
        mediaPlayer.start();

        Log.i("receiver", "received");

    }
}
