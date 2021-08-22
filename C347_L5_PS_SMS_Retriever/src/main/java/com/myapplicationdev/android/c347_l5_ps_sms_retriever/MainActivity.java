package com.myapplicationdev.android.c347_l5_ps_sms_retriever;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

// Todo: MainActivity is done by Sf
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentFirst f1 = new FragmentFirst();
        fragmentTransaction.replace(R.id.frame1, f1);

        FragmentSecond f2 = new FragmentSecond();
        fragmentTransaction.replace(R.id.frame2, f2);

        fragmentTransaction.commit();
    }
}