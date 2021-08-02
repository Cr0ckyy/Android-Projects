package com.myapplicationdev.android.c347_l11_ex1_demo_alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnSetAlarm;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSetAlarm = findViewById(R.id.btnAlarm);

        btnSetAlarm.setOnClickListener(v -> {

            // a calendar with the default time zone and locale.
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 2);

            //Create a new PendingIntent and add it to the AlarmManager
            Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
            int reqCode = 12345; // request code

            // A description of an intent and the intended action to be taken with it.
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            MainActivity.this,
                            reqCode,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                            /*If the described PendingIntent already exists,
                            the current one should be canceled before generating a new one,
                            according to this flag.*/
                    );

            // Get AlarmManager instance
            am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

            // Set the alarm
            am.set(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    pendingIntent);
        });

    }//end of onCreate
}//end of class
