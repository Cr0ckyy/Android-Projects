package com.myapplicationdev.android.c347_l9_ps_getting_my_location;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LocationListActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    Button btnRefresh;
    TextView tvRecords;
    ListView lvLocations;
    StringBuilder dataLines;
    String myFolderLocation, line;
    File folder;
    FileReader reader;
    BufferedReader bufferedReader;
    String[] dataLinesArray;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        tvRecords = findViewById(R.id.tvRecords);
        lvLocations = findViewById(R.id.lvLocations);
        btnRefresh = findViewById(R.id.btnRefresh);

        // TODO: Folders creation
        myFolderLocation = getFilesDir().getAbsolutePath() + "/Folder";
        folder = new File(myFolderLocation);

        // TODO: See whether file/directory specified by this abstract pathname exists.
        if (folder.exists()) {

            // A mutable character sequence object.
            dataLines = new StringBuilder();

            try {

                reader = new FileReader(folder);
                bufferedReader = new BufferedReader(reader);

                // A BufferedReader object that reads a single line of text.
                line = bufferedReader.readLine();

                while (line != null) {
                    dataLines.append(line).append("\n");
                    line = bufferedReader.readLine();
                }

                dataLinesArray = dataLines.toString().split("\n");
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, dataLinesArray);

                lvLocations.setAdapter(adapter);
                tvRecords.setText("Number of Records: " + dataLinesArray.length);

                bufferedReader.close();
                reader.close();
            } catch (Exception e) {

                Toast.makeText(LocationListActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            Log.d("Content", dataLines.toString());
        } else {
            Log.d("Content", "The file is already existed");
        }

        // TODO: Refresh line data
        btnRefresh.setOnClickListener((View v) -> {

            myFolderLocation = getFilesDir().getAbsolutePath() + "/Folder";
            folder = new File(myFolderLocation, "location.txt");

            if (folder.exists()) {
                dataLines = new StringBuilder();

                try {
                    reader = new FileReader(folder);
                    bufferedReader = new BufferedReader(reader);

                    line = bufferedReader.readLine();

                    // adding lines into data
                    while (line != null) {
                        dataLines.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }

                    // Split the data after it has been added by adding a new line.
                    dataLinesArray = dataLines.toString().split("\n");
                    adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1, dataLinesArray);

                    lvLocations.setAdapter(adapter);
                    tvRecords.setText("Number of Records: " + dataLinesArray.length);

                    // Closes the stream and releases any associated system resources
                    bufferedReader.close();
                    reader.close();

                } catch (Exception e) {
                    Toast.makeText(LocationListActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Log.d("Content", dataLines.toString());
            }

            Toast.makeText(LocationListActivity.this,
                    "The records have been refreshed.",
                    Toast.LENGTH_LONG).show();
        });
    }
}
