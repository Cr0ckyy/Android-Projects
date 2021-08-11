package com.myapplicationdev.android.c347_l11_ex3_demo_scheduled_notification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    int requestCode = 12345;
    Button buttonSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSchedule = findViewById(R.id.btnSchedule);

        buttonSchedule.setOnClickListener((View view) -> {

            // a calendar with the default time zone and locale.
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 2);

            //Create a new PendingIntent and add it to the Scheduled NotificationReceiver
            Intent intent = new Intent(MainActivity.this, ScheduledNotificationReceiver.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(MainActivity.this,
                            requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Get AlarmManager instance
            AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

            // Set the alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        });
    }
}