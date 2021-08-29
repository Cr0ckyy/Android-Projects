package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowSong extends AppCompatActivity {
    Button show5;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Song> newSongs;
    ArrayList<Song> newSongList;
    ArrayList<Song> filteredYearSongList;
    ArrayList<Integer> numList;
    ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        lv = this.findViewById(R.id.lvSongs);
        show5 = this.findViewById(R.id.buttonShow5);

        Intent i = getIntent();

        DBHelper dbh = new DBHelper(ShowSong.this);

        newSongs = new ArrayList<>();
        newSongs.clear();
        newSongs.addAll(dbh.getAllSongs());
        newSongList = new ArrayList<>();

        dbh.close();

        aa = new SongAdapter(ShowSong.this, R.layout.row, newSongs);
        lv.setAdapter(aa);

        lv.setOnItemClickListener((parent, view, position, identity) -> {
            Song target = newSongs.get(position);
            Intent i1 = new Intent(ShowSong.this, ModifySong.class);
            i1.putExtra("data", target);
            startActivityForResult(i1, 9);
        });

        Spinner dynamicSpinner = findViewById(R.id.dynamic_spinner);

        numList = new ArrayList<>();
        numList.add(-1);
        for (int c = 0; c < newSongs.size(); c++) {
            if (!numList.contains(newSongs.get(c).getYear())) {
                numList.add(newSongs.get(c).getYear());
            }
        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numList);
        filteredYearSongList = new ArrayList<>();
        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                int selectedYear = numList.get(position);
                filteredYearSongList.clear();
                for (int e = 0; e < newSongs.size(); e++) {
                    if (newSongs.get(e).getYear() == selectedYear) {
                        filteredYearSongList.add(newSongs.get(e));
                    }

                }
                if (selectedYear != -1) {
                    aa = new SongAdapter(ShowSong.this, R.layout.row, filteredYearSongList);
                    lv.setAdapter(aa);
                } else {
                    aa = new SongAdapter(ShowSong.this, R.layout.row, newSongs);
                    lv.setAdapter(aa);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        show5.setOnClickListener(v -> {
            for (int d = 0; d < newSongs.size(); d++) {
                if (newSongs.get(d).getStars() == 5) {
                    newSongList.add(newSongs.get(d));
                }
            }
            aa = new SongAdapter(ShowSong.this, R.layout.row, newSongList);
            lv.setAdapter(aa);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper dbh = new DBHelper(ShowSong.this);
            newSongs.clear();
            newSongs.addAll(dbh.getAllSongs());
            dbh.close();


            numList.clear();
            numList.add(-1);
            for (int c = 0; c < newSongs.size(); c++) {
                if (!numList.contains(newSongs.get(c).getYear())) {
                    numList.add(newSongs.get(c).getYear());
                }
            }

            aa.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }
}