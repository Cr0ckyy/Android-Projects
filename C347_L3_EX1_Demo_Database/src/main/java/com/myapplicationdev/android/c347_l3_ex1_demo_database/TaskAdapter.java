package com.myapplicationdev.android.c347_l3_ex1_demo_database;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> tasks;
    Context context;
    TextView idTextView, descriptionTextView, dateTextView;

    public TaskAdapter(@NonNull Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, parent, false);

        Task current = tasks.get(position);

        idTextView = rowView.findViewById(R.id.tvId);
        descriptionTextView = rowView.findViewById(R.id.tvDescription);
        dateTextView = rowView.findViewById(R.id.tvDate);

        idTextView.setText("" + current.getId());
        descriptionTextView.setText(current.getDescription());
        dateTextView.setText(current.getDate());

        return rowView;
    }
}

