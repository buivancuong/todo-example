package com.umbala.cuongbv.todo.ui.edit;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.umbala.cuongbv.todo.R;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer = MediaPlayer.create(this, R.raw.reminder);
//        mediaPlayer.start();

        return START_STICKY;
    }
}
