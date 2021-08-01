package com.myapplicationdev.android.c347_l4_ps_ps_ndp_songs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplicationdev.android.c347_l4_ps_ps_ndp_songs.Song;

public class ModifyActivity extends AppCompatActivity {

    TextView tvSongId;
    Song song;
    EditText etSongTitle, etSingers, etYear;
    Button btnUpdate, btnDelete, btnCancel;
    RadioGroup radioGroupStars;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);


        tvSongId = findViewById(R.id.tvSongId);
        etSongTitle = findViewById(R.id.etSongTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        radioGroupStars = findViewById(R.id.radioGroupStars);

        Intent i = getIntent();
        song = (Song) i.getSerializableExtra("song");

        tvSongId.setText(song.getId() + "");
        etYear.setText(song.getYear() + "");
        etSingers.setText(song.getSingers());
        etSongTitle.setText(song.getTitle());


        btnCancel.setOnClickListener(view -> finish());
        btnUpdate.setOnClickListener(view -> {
            int checking = radioGroupStars.getCheckedRadioButtonId();
            RadioButton rbSelected = findViewById(checking);
            int stars = Integer.parseInt(rbSelected.getText().toString());
            DBHelper dbh = new DBHelper(ModifyActivity.this);
            song.setYear(Integer.parseInt(etYear.getText().toString()));
            song.setSingers(etSingers.getText().toString());
            song.setTitle(etSongTitle.getText().toString());
            song.setStars(stars);

            dbh.updateSong(song);
            dbh.close();
            Intent i1 = new Intent();
            setResult(RESULT_OK, i1);
            finish();
        });

    }
}

