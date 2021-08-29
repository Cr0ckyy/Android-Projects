package com.myapplicationdev.android.c347_l11_ex1_demo_alarmmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button buttonSetAlarm;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSetAlarm = findViewById(R.id.btnAlarm);

        buttonSetAlarm.setOnClickListener((View view) -> {

            // a calendar with the default time zone and locale.
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 2); // add the time it takes to show up

            //Create a new PendingIntent and add it to the AlarmManager
            Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
            int requestCode = 12345;

            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            MainActivity.this,
                            requestCode,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                   /*If the described PendingIntent already exists,
                            the current one should be canceled before generating a new one,
                            according to this flag.*/
                    );

            // Get AlarmManager instance
            alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

            // Set the alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        });

    }
}