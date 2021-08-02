package com.myapplicationdev.android.c347_l10_ex2_demo_music_player;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        int permissionCheck = PermissionChecker.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            Log.i("Permission", "Permission not granted");
            return;
        }

        btnStart.setOnClickListener((View view) -> {
            // starting the service
            startService(new Intent(MainActivity.this, MyService.class));
        });

        btnStop.setOnClickListener((View view) -> {
            // stopping the service
            stopService(new Intent(MainActivity.this, MyService.class));
        });

    }

}