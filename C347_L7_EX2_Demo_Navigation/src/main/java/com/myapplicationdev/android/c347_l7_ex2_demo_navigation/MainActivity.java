package com.myapplicationdev.android.c347_l7_ex2_demo_navigation;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button btnNewActivity;
    FloatingActionButton floatingActionButton;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        // TODO: enable "Return arrow" function in ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnNewActivity = findViewById(R.id.btnNewActivity);
        floatingActionButton = findViewById(R.id.fab);

        btnNewActivity.setOnClickListener((View v) -> {
            Intent i = new Intent(MainActivity.this, NewActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "New Activity Button is pressed", Toast.LENGTH_SHORT).show();
        });

        floatingActionButton.setOnClickListener((View v) ->
                Toast.makeText(getApplicationContext(), "Floating Action Button is pressed", Toast.LENGTH_SHORT).show()
        );
    }
}

