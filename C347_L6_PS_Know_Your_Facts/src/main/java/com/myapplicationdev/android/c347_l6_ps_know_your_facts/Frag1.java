package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.myapplicationdev.android.c347_l6_ps_know_your_facts.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

// Todo by Jun Kai
public class Frag1 extends Fragment {
    ImageView iv1;
    Button btnColor;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);


        iv1 = view.findViewById(R.id.iv);
        btnColor = view.findViewById(R.id.btnColor);

        String imageUrl = "https://wtffunfact.com/wp-content/uploads/2021/05/WTF-Fun-Fact-Arctic-Foxs-Colorful-Fur.png";
        Picasso.with(view.getContext()).load(imageUrl).into(iv1);

        btnColor.setOnClickListener(v -> {
            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256),
                    random.nextInt(256));
            view.setBackgroundColor(color);
        });
        return view;
    }
}

