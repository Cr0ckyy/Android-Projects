package com.myapplicationdev.android.c347_l10_ex1_demo_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    boolean started;
    final DownloadBinder downloadBinder = new DownloadBinder();

    @Override
    // TODO: Return the communication channel
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    @Override
    // TODO: Called when the system first creates the service
    public void onCreate() {
        Log.d("MyService", "Service created");
        super.onCreate();
    }

    @Override
    // TODO: Called by the system when a client starts the service
    public int onStartCommand(Intent intent, int flags, int startId) {

        // if haven't started yet
        if (!started) {
            started = true;
            Log.d("MyService", "Service started");

            // if started
        } else {
            Log.d("MyService", "Service is still running");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    // TODO: The system calls this when a service is no longer needed.
    public void onDestroy() {
        Log.d("MyService", "Service exited");
        super.onDestroy();
    }

    static class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public void getProgress() {
            Log.d("MyService", "getProgress executed");
        }
    }

}