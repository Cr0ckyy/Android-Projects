package com.myapplicationdev.android.c347_l2_ex1_demo_explicit_intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int requestCodeForSupermanStats = 1;
    int requestCodeForBatmanStats = 2;
    TextView tvSuperman, tvBatman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSuperman = findViewById(R.id.textViewSuperman);
        tvBatman = findViewById(R.id.textViewBatman);

        tvSuperman.setOnClickListener((View view) ->
                goToActivity(new Hero("Superman", 100, 60), requestCodeForSupermanStats)
        );

        tvBatman.setOnClickListener((View view) ->
                goToActivity(new Hero("Batman", 60, 90), requestCodeForBatmanStats)
        );
    }

    private void goToActivity(Hero hero, int requestCode) {
        Intent i = new Intent(MainActivity.this, HeroStatsActivity.class);
        // TODO: passing Hero data to HeroStatsActivity
        i.putExtra("hero", hero);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String like = data.getStringExtra("like");
            String statement = "";

            if (requestCode == requestCodeForSupermanStats) {
                statement = "You " + like + " Superman";
            }

            if (requestCode == requestCodeForBatmanStats) {
                statement = "You " + like + " Batman";
            }

            Toast.makeText(MainActivity.this, statement, Toast.LENGTH_LONG).show();
        }
    }
}