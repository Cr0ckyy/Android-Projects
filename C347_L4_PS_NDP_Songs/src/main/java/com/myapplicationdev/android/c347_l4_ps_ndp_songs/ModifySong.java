package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ModifySong extends AppCompatActivity {
    TextView tvId;
    EditText etSinger, etTitle, etYear;
    RadioGroup rg;
    RadioButton rb;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        tvId = findViewById(R.id.tvDisplaySongID);
        etSinger = findViewById(R.id.etUpdateSinger);
        etTitle = findViewById(R.id.etUpdateName);
        etYear = findViewById(R.id.etUpdateYear);
        rg = findViewById(R.id.rgUpdate);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        tvId.setText("ID:" + data.get_id());
        etSinger.setText(data.getSingers());
        etTitle.setText(data.getTitle());
        etYear.setText(String.valueOf(data.getYear()));


        btnUpdate.setOnClickListener((View v) -> {
            DBHelper dbh = new DBHelper(ModifySong.this);
            rb = findViewById(rg.getCheckedRadioButtonId());

            dbh.updateSong(new Song(
                    data.get_id(),
                    etTitle.getText().toString(),
                    etSinger.getText().toString(),
                    Integer.parseInt(etYear.getText().toString()),
                    Integer.parseInt(rb.getText().toString()))
            );
            dbh.close();

            Intent intent = new Intent(ModifySong.this, ShowSong.class);
            setResult(RESULT_OK, intent);
            finish();

        });


        btnDelete.setOnClickListener((View v) -> {
            DBHelper dbh = new DBHelper(ModifySong.this);
            dbh.deleteSong(data.get_id());
            dbh.close();
            Intent intent = new Intent(ModifySong.this, ShowSong.class);

            setResult(RESULT_OK, intent);
            finish();

        });
        btnCancel.setOnClickListener((View v) -> {
            Intent intent = new Intent(ModifySong.this, ShowSong.class);
            setResult(RESULT_OK, intent);
            finish();

        });


    }
}