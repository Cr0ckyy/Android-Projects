package com.myapplicationdev.android.c347_l5_ex1_demo_fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Obtain the FragmentManager object.
        FragmentManager fragmentManager = getSupportFragmentManager();

        // TODO: Obtain the Fragment Transaction object from the FragmentManager.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // TODO: Using the default constructor, create the fragment objects.
        Fragment f1 = new FragmentFirst();

        // TODO: The Fragment will take the place of the ViewGroup.
        fragmentTransaction.replace(R.id.frame1, f1);


        // Replace – Replaces the layout with a Fragment
        // Add – Add a Fragment into a layout
        Fragment f2 = new FragmentSecond();
        fragmentTransaction.replace(R.id.frame2, f2);

        // TODO: Only after the commit function would the fragment be added.
        fragmentTransaction.commit();
    }
}