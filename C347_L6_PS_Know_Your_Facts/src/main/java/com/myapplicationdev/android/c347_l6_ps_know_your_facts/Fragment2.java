package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Random;


public class Fragment2 extends Fragment {
    Button btnChangeColor;
    ImageView iv1, iv2, iv3;
    LinearLayout layout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO: Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        iv1 = view.findViewById(R.id.iv);
        iv2 = view.findViewById(R.id.iv2);
        iv3 = view.findViewById(R.id.iv3);
        btnChangeColor = view.findViewById(R.id.btnColorFrag2);
        layout = view.findViewById(R.id.layoutfor2);

        String imageURL1 = "https://wtffunfact.com/wp-content/uploads/2021/05/WTF-Fun-Fact-Crassuss-Fire-Brigade-1.png";
        String imageURL2 = "https://wtffunfact.com/wp-content/uploads/2021/05/WTF-Fun-Fact-Arctic-Foxs-Colorful-Fur.png";
        String imageURL3 = "https://wtffunfact.com/wp-content/uploads/2021/05/WTF-Fun-Fact-From-NFL-To-Serial-Killer.png";

        // TODO: load URL image into ImageViews with Picasso
        Picasso.with(getActivity()).load(imageURL1).into(iv1);
        Picasso.with(getActivity()).load(imageURL2).into(iv2);
        Picasso.with(getActivity()).load(imageURL3).into(iv3);


        // TODO: color change for layout
        Random randomColorNo = new Random();
        btnChangeColor.setOnClickListener((View v) -> {
            Log.d("Button", "Button Pressed");
            float r = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float g = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float b = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            int rgb = android.graphics.Color.rgb(r, g, b);
            layout.setBackgroundColor(rgb);
        });

        return view;
    }
}