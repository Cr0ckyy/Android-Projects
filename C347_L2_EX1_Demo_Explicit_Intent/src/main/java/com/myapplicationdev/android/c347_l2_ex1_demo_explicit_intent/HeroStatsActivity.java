package com.myapplicationdev.android.c347_l2_ex1_demo_explicit_intent;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeroStatsActivity extends AppCompatActivity {

    TextView tvName, tvStrength, tvTechnicalProwess;
    Button btnLike, btnDislike;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_stats);

        Intent i = getIntent();

        // TODO: getting the passed Hero data from MainActivity
        Hero hero = (Hero) i.getSerializableExtra("hero");

        tvName = findViewById(R.id.textViewName);
        tvStrength = findViewById(R.id.textViewStrength);
        tvTechnicalProwess = findViewById(R.id.textViewTechnicalProwess);
        btnLike = findViewById(R.id.buttonLike);
        btnDislike = findViewById(R.id.buttonDislike);


        // TODO: setting of UI
        tvName.setText(hero.getName());
        tvStrength.setText("Strength: " + hero.getStrength());
        tvTechnicalProwess.setText("Technical: " + hero.getTechnicalProwess());

        btnLike.setOnClickListener(view -> buttonDidClickWithLike(true));
        btnDislike.setOnClickListener(view -> buttonDidClickWithLike(false));
    }

    private void buttonDidClickWithLike(boolean like) {
        Intent i = new Intent();
        i.putExtra("like", like ? "liked" : "disliked");
        setResult(RESULT_OK, i);
        finish();
    }
}