package com.myapplicationdev.android.c347_l1_ex2_demo_imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ivDay2, ivDay3, ivDay4, ivDay5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ivDay3 = findViewById(R.id.imageView3);
        ivDay2 = findViewById(R.id.imageView2);
        ivDay4 = findViewById(R.id.imageView4);
        ivDay5 = findViewById(R.id.imageView5);

        ivDay2.setImageResource(R.drawable.day2);
        ivDay3.setImageResource(R.drawable.day3);
        ivDay4.setImageResource(R.drawable.day4);
        ivDay5.setImageResource(R.drawable.day5);



    }
}