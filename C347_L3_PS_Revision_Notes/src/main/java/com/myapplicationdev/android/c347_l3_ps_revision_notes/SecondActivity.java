package com.myapplicationdev.android.c347_l3_ps_revision_notes;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


// TODO: Create the necessary objects in the SecondActivity to bind the ListView components.
public class SecondActivity extends AppCompatActivity {

    // Todo: Step 2: Declare ArrayList , ArrayAdapter and other variables
    RevisionNotesArrayAdapter revisionNotesArrayAdapter;
    ArrayList<Note> noteArrayList;
    ListView lvNotes;
    TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Todo: Step 3: Bind variables
        ListView lvNotes = findViewById(R.id.lvNotes);
        TextView tvNote = findViewById(R.id.textViewNote);

        //TODO implement the Custom ListView
        DBHelper db = new DBHelper(SecondActivity.this);
        noteArrayList = db.getAllNotes();

        // Todo: Step 4: Attach the ArrayAdapter to the ListView
        // Link this Activity object, the row.xml layout for
        //  each row and the Note String array together
        revisionNotesArrayAdapter = new RevisionNotesArrayAdapter(this, R.layout.row, noteArrayList);
        lvNotes.setAdapter(revisionNotesArrayAdapter);

    }


}

