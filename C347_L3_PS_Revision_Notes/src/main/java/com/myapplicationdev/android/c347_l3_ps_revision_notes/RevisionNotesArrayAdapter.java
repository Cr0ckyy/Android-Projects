package com.myapplicationdev.android.c347_l3_ps_revision_notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Todo: Step 3: Create a CustomAdapter class which extends  from the ArrayAdapter class.
public class RevisionNotesArrayAdapter extends ArrayAdapter<Note> {

    // Obtain relevant elements
    Context context;
    ArrayList<Note> notes;
    int resource;
    TextView tvNote;
    ImageView iv1, iv2, iv3, iv4, iv5;

    public RevisionNotesArrayAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
        this.context = context;

        // Store the notes that is passed to this adapter
        this.notes = notes;

        // Store Context object as we would need to use it later
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Todo: Obtain the LayoutInflater object
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Todo: "Inflate" the View for each row
        // "Inflate" the row.xml as the layout for the View object
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(resource, parent, false);

        // Todo: Obtain the UI components through binding
        // get UI objects from the row.xml
        iv1 = rowView.findViewById(R.id.imageView1star);
        iv2 = rowView.findViewById(R.id.imageView2star);
        iv3 = rowView.findViewById(R.id.imageView3star);
        iv4 = rowView.findViewById(R.id.imageView4star);
        iv5 = rowView.findViewById(R.id.imageView5star);
        tvNote = rowView.findViewById(R.id.textViewNote);

        // Todo: Obtain the Note Object Information based on the position
        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the Note at the same index.
        Note currentNote = notes.get(position);

        // Todo: Set values to the TextView to display the corresponding information

        // Set the TextView to show the Note
        tvNote.setText(currentNote.getNoteContent());

        // Set the image to star accordingly
        //Check if the property for starts >= 5, if so, "light" up the stars
        int stars = currentNote.getStars();

        switch (stars) {
            case 1:
                iv1.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case 2:
                iv2.setImageResource(android.R.drawable.btn_star_big_on);
                iv1.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case 3:
                iv3.setImageResource(android.R.drawable.btn_star_big_on);
                iv2.setImageResource(android.R.drawable.btn_star_big_on);
                iv1.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case 4:
                iv4.setImageResource(android.R.drawable.btn_star_big_on);
                iv3.setImageResource(android.R.drawable.btn_star_big_on);
                iv2.setImageResource(android.R.drawable.btn_star_big_on);
                iv1.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            default:
                iv5.setImageResource(android.R.drawable.btn_star_big_on);
                iv4.setImageResource(android.R.drawable.btn_star_big_on);
                iv3.setImageResource(android.R.drawable.btn_star_big_on);
                iv2.setImageResource(android.R.drawable.btn_star_big_on);
                iv1.setImageResource(android.R.drawable.btn_star_big_on);

        }

        // Return the nicely done up View to the ListView
        return rowView;
    }


}

