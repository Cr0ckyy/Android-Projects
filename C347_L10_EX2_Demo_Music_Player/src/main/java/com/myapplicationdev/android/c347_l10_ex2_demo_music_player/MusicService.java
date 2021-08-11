package com.myapplicationdev.android.c347_l10_ex2_demo_music_player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class MusicService extends Service {

    // declaring object of MediaPlayer
    MediaPlayer player = new MediaPlayer();
    boolean started;
    static final String TAG = "MusicService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    // execution of service will start on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!started) {
            started = true;
            Log.d(TAG, "Service started");
        } else {
            Log.d(TAG, "Service is still running");
        }

        try {
            File file = new File(
                    Environment
                            .getExternalStorageDirectory()
                            .getAbsolutePath() + "/MyFolder",
                    "Something Happened on the Way to Heaven.mp3");

            // specify the path of the audio file
            player.setDataSource(file.getPath());
            player.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // providing the boolean value as true to play the audio on loop
        player.setLooping(true);

        // starting the process
        player.start();

        // returns the status of the program
        return START_STICKY;

    }

    @Override
// execution of the service will stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
        player.stop();

        Log.d(TAG, "Service exited");
    }

}
