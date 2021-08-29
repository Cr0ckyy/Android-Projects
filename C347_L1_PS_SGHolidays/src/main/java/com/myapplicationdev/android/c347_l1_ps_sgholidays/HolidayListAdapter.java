package com.myapplicationdev.android.c347_l1_ps_sgholidays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HolidayListAdapter extends ArrayAdapter<Holiday> {
    private ArrayList<Holiday> holidayType;
    private Context context;
    private TextView holidayTitleView, holidayDateView;
    private ImageView holidayImageView;

    public HolidayListAdapter(@NonNull Context context, int resource, ArrayList<Holiday> holidayType) {
        super(context, resource, holidayType);
        this.holidayType = holidayType;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.holiday_row, parent, false);

        Holiday thisHoliday = holidayType.get(position);

        holidayImageView = rowView.findViewById(R.id.holidayImage);
        holidayTitleView = rowView.findViewById(R.id.holidayTitle);
        holidayDateView = rowView.findViewById(R.id.dateText);

        holidayImageView.setImageResource(thisHoliday.getImageId());
        holidayDateView.setText(thisHoliday.getDate());
        holidayTitleView.setText(thisHoliday.getDayName());

        return rowView;
    }
}
