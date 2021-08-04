package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

// Todo by Jun Kai
public class Frag2 extends Fragment {

    TextView tv1, tv2, tv3;
    Button btnColor1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag2, container, false);

        tv1 = view.findViewById(R.id.textView1);
        tv2 = view.findViewById(R.id.textView2);
        tv3 = view.findViewById(R.id.textView3);
        btnColor1 = view.findViewById(R.id.btnColor1);

        btnColor1.setOnClickListener(v -> {

            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256),
                    random.nextInt(256));
            view.setBackgroundColor(color);
        });
        return view;

    }

}

