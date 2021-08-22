package com.myapplicationdev.android.c347_l6_ps_know_your_facts;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class Fragment1 extends Fragment {
    Button btnChangeColor;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        btnChangeColor = view.findViewById(R.id.btnColorFrag1);

        // Creates a color random number generator
        Random randomColorNo = new Random();

        // TODO: color change for layout
        btnChangeColor.setOnClickListener((View v) -> {
            float red = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float green = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float blue = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            int rgb = Color.rgb(red, green, blue);
            view.setBackgroundColor(rgb);
        });


        return view;
    }
}