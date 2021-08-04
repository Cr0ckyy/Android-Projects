package com.myapplicationdev.android.c347_l7_ps_my_data_book;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {
    ActionBar actionBar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // TODO: setting of actionBar
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.iv);

        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/80/Republic_Polytechnic_Logo.jpg";

        // TODO: setting of Picasso
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);

    }
}