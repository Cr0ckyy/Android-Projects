package com.myapplicationdev.android.c347_l6_ex1_demo_action_bar;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // TODO: Initialize the contents of the Activity's standard options menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //todo: Inflate the menu in onCreateOptionsMenu
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

