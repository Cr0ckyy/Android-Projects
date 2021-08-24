package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SummaryAdapter extends ArrayAdapter<CategorySummary> {

    ArrayList<CategorySummary> myCategorySummaryArrayList;
    Context context;
    TextView myTextViewCategoryName, myTextViewCategoryCount;
    LinearLayout myLinearLayout;

    public SummaryAdapter(Context context, int resource, ArrayList<CategorySummary> objects) {
        super(context, resource, objects);
        myCategorySummaryArrayList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.activity_summary_list, parent, false);

        myLinearLayout = rowView.findViewById(R.id.summary_list);
        myTextViewCategoryName = rowView.findViewById(R.id.tvCategoryName);
        myTextViewCategoryCount = rowView.findViewById(R.id.tvCategoryCount);

        if (position % 2 == 1) {
            myLinearLayout.setBackgroundColor(Color.parseColor("#4169e1"));
        }

        CategorySummary myCurrentCategorySummary = myCategorySummaryArrayList.get(position);

        myTextViewCategoryName.setText(myCurrentCategorySummary.getName());
        myTextViewCategoryCount.setText(String.valueOf(myCurrentCategorySummary.getCount()));

        return rowView;
    }
}
