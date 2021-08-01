package com.myapplicationdev.android.c347_l3_ex1_demo_database;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;

    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(view -> {
            DBHelper db = new DBHelper(MainActivity.this);
            db.insertTask("Happy Star Wars Day!", "4 May 2021");
            db.close();
        });

        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);

        tasks = new ArrayList<>();

        ArrayAdapter<Task> adapter = new TaskAdapter(this, R.layout.row, tasks);
        lv.setAdapter(adapter);

        btnGetTasks.setOnClickListener(view -> {
            DBHelper db = new DBHelper(MainActivity.this);

            tasks.clear();

            // TODO : Direct assigning will result in the adapter referencing the incorrect instance.
            String currentResults = tvResults.getText().toString();
            tasks.addAll(db.getTaskContent(currentResults));
            db.close();
            String text = "";

            int i = 0;
            for (Task task : tasks) {
                Log.d("Database Content: ", i + ". " + tasks.get(i));
                text += i + ". " + task.getDescription() + "\n";
                i++;
            }
            tvResults.setText(text);
            adapter.notifyDataSetChanged();
        });

    }
}

