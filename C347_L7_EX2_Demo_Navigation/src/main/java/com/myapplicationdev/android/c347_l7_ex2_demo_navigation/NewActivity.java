package com.myapplicationdev.android.c347_l7_ex2_demo_navigation;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        // TODO: enable "Return arrow" function in ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}

