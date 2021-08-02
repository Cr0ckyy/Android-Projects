package com.myapplicationdev.android.c347_l11_ex3_demo_scheduled_notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int reqCode = 12345;
    Button btnSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSchedule = findViewById(R.id.btnSchedule);

        btnSchedule.setOnClickListener(v -> {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 5);

            Intent intent = new Intent(MainActivity.this,
                    ScheduledNotificationReceiver.class);

            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this, reqCode,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager)
                    getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    pendingIntent);

        });
    }
}