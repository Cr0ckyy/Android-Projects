package com.myapplicationdev.android.c347_l4_ps_ps_ndp_songs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etSongTitle, etSingers, etYear;
    RadioGroup radioGroupStars;
    Button btnInsert, btnShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSongTitle = findViewById(R.id.etSongTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        radioGroupStars = findViewById(R.id.radioGroupStars);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);

        btnInsert.setOnClickListener(v -> {


            // TODO: obtained the retrieved data from the Song Form
            String title = etSongTitle.getText().toString();
            String singer = etSingers.getText().toString();
            int year = Integer.parseInt(etYear.getText().toString());

            // obtain the star value
            int checkedRadioButtonId = radioGroupStars.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(checkedRadioButtonId);
            int star = Integer.parseInt(selectedRadioButton.getText().toString());


            DBHelper dbh = new DBHelper(MainActivity.this);
            long inserted_id = dbh.insertSong(title, singer, year, star);
            dbh.close();

            if (inserted_id != -1) {
                Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();

            }
        });
        btnShow.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            startActivity(intent);
        });
    }

}

