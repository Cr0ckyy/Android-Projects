package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ColourAdapter extends ArrayAdapter<Colour> {

    public static final String LOG_TAG = ColourAdapter.class.getName();
    ArrayList<Colour> alColour;
    Context context;

    public ColourAdapter(Context context, int resource, ArrayList<Colour> objects) {
        super(context, resource, objects);
        alColour = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.colour_row, parent, false);

        TextView tvColour = rowView.findViewById(R.id.tvColour);
        Colour colour = alColour.get(position);

        tvColour.setText(colour.getColourName());

        Log.d(LOG_TAG, "Colour Data: "
                + colour.getColourName());
        return rowView;
    }

}