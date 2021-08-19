package com.myapplicationdev.android.c347_l4_ps_ps_ndp_songs;

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


public class ShowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Song> al;
    ArrayAdapter aa;
    ListView lv;
    Button btnShow;

    // TODO Create array for spinner
    ArrayList<Integer> arYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        btnShow = findViewById(R.id.btnShow);

        lv = findViewById(R.id.lv);
        al = new ArrayList<>();
        DBHelper db = new DBHelper(ShowActivity.this);
        al = db.getAllNotes();
        aa = new SongAdapter(
                this, R.layout.row, al
        );
        lv.setAdapter(aa);

        lv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long identity) -> {
            Song song = al.get(position);
            Intent i = new Intent(ShowActivity.this,
                    ModifyActivity.class);
            i.putExtra("song", song);
            startActivityForResult(i, 9);
        });

        // TODO Show the spinner

        // ArrayList to hold the years
        arYears = new ArrayList<>();
        arYears.add(0);
        arYears.addAll(db.getYears());

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.spYear);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arYears);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(ShowActivity.this);
            al = new ArrayList<>();
            al = db.getAllNotes();
            aa = new SongAdapter(this, R.layout.row, al);
            lv.setAdapter(aa);
        }
    }//

    // TODO Create override methods for spinner
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Integer selectedYear = arYears.get(position);
        DBHelper db = new DBHelper(ShowActivity.this);
        al.clear();
        if (selectedYear != 0) {
            al.addAll(db.getSongsByYear(selectedYear));
        } else {
            al.addAll(db.getAllNotes());
        }
        db.close();
        aa.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

