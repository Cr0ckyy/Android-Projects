package com.myapplicationdev.android.c347_l11_ps_task_manager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDescription, etTime;
    Button btnAddTask, btnCancel;
    Calendar calendar;
    String name, description;
    DBHelper db;
    int time, notificationID, requestCode;
    long insertId;
    Intent intent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etTime = findViewById(R.id.etTime);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAddTask.setOnClickListener((View v) -> {
            name = etName.getText().toString();
            description = etDescription.getText().toString();
            time = Integer.parseInt(etTime.getText().toString());

            db = new DBHelper(AddActivity.this);
            insertId = db.addTask(name, description);
            db.close();

            if (insertId != -1) {
                notificationID = 888;
                requestCode = 123;

                calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, time);

                intent = new Intent(AddActivity.this,
                        ScheduledNotificationReceiver.class);

                intent.putExtra("name", name);
                intent.putExtra("description", description);

                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(
                                AddActivity.this, requestCode,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT
                        );

                alarmManager = (AlarmManager)
                        getSystemService(AddActivity.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent
                );

                finish();
            }
        });

        btnCancel.setOnClickListener((View v) ->
                finish()
        );

    }

}