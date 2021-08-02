package com.myapplicationdev.android.c347_l10_ps_enhanced_getting_my_location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DetectorService extends Service {
    boolean started;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d("DetectorService", "Service created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!started) {
            started = true;
            Log.d("DetectorService", "Service started");
        } else {
            Log.d("DetectorService", "Service is still running");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("DetectorService", "Service exited");
        super.onDestroy();
    }

}