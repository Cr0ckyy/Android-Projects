package com.myapplicationdev.android.c347_l11_ps_task_manager2;


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
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            int time = Integer.parseInt(etTime.getText().toString());

            DBHelper db = new DBHelper(AddActivity.this);
            long insertId = db.addTask(name, description);
            db.close();

            if (insertId != -1) {
                int requestCode = 123;

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, time);

                Intent intent = new Intent(AddActivity.this,
                        ScheduledNotificationReceiver.class);

                intent.putExtra("name", name);
                intent.putExtra("description", description);

                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(
                        AddActivity.this, requestCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(AddActivity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                finish();
            }
        });

    }

}