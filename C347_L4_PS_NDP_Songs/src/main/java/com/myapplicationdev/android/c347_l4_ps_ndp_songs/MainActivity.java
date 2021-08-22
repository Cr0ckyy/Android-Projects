package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etTitle, etSinger, etYear;
    Button btnInsert, btnShow;
    RadioGroup rgStars;
    RadioButton rb;
    ArrayList<Song> al;
    DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSingerName);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rg);
        btnInsert = findViewById(R.id.buttonInsert);
        btnShow = findViewById(R.id.buttonShowList);
        dbh = new DBHelper(MainActivity.this);
        al = new ArrayList<>();

        btnInsert.setOnClickListener((View v) -> {
            // TODO: obtained the retrieved data from the Song Form
            String title = etTitle.getText().toString();
            String singer = etSinger.getText().toString();
            int year = Integer.parseInt(etYear.getText().toString());

            // obtain the star value
            rb = findViewById(rgStars.getCheckedRadioButtonId());


            long inserted_id = dbh.insertSong(title, singer, year, Integer.parseInt(rb.getText().toString()));
            dbh.close();

            if (inserted_id != -1) {
                Toast.makeText(MainActivity.this, "Insert successful",
                        Toast.LENGTH_SHORT).show();
            }

        });

        btnShow.setOnClickListener((View v) -> {
            Intent i = new Intent(MainActivity.this, ShowSong.class);

            startActivityForResult(i, 9);

        });

    }

}