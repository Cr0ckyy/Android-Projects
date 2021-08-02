package com.myapplicationdev.android.c347_l10_ex1_demo_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnStop, btnBind, btnUnbind;

    MyService.DownloadBinder downloadBinder;
    Intent i, bindIntent;

    final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnBind = findViewById(R.id.btnBind);
        btnUnbind = findViewById(R.id.btnUnbind);


        btnStart.setOnClickListener((View v) -> {
            i = new Intent(MainActivity.this, MyService.class);
            startService(i);
        });


        btnStop.setOnClickListener((View v) -> {
            i = new Intent(MainActivity.this, MyService.class);
            stopService(i);
        });

        btnBind.setOnClickListener((View v) -> {
            bindIntent = new Intent(MainActivity.this, MyService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
        });

        btnUnbind.setOnClickListener((View v) ->
                unbindService(connection)
        );


    }

}