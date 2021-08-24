package com.myapplicationdev.android.c347_l11_ps_task_manager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTasks;
    Button btnAddTask;
    ArrayAdapter arrayAdapter;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTasks = findViewById(R.id.lvTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        DBHelper dbh = new DBHelper(MainActivity.this);
        tasks = new ArrayList<>();
        tasks = dbh.getAllTasks();
        dbh.close();

        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, tasks);
        lvTasks.setAdapter(arrayAdapter);

        btnAddTask.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        tasks = new ArrayList<>();
        DBHelper dbh = new DBHelper(MainActivity.this);
        tasks.addAll(dbh.getAllTasks());
        dbh.close();
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, tasks);
        lvTasks.setAdapter(arrayAdapter);
    }
}