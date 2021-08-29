package com.myapplicationdev.android.c347_l1_ps_sgholidays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HolidayListActivity extends AppCompatActivity {

    TextView typeTitleView;
    ListView holidayList;
    ArrayAdapter adapter;
    ArrayList<Holiday> holidays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_list);

        holidayList = findViewById(R.id.holidayList);
        typeTitleView = findViewById(R.id.typeTitleView);

        holidays = new ArrayList<>();

        Intent i = getIntent();
        String holidayType = i.getStringExtra("holidayType");
        typeTitleView.setText(holidayType);
        if (holidayType.equals("Secular")) {
            holidays.add(new Holiday("New Year's Day",
                    "1 Jan 2021",
                    R.drawable.new_year));
            holidays.add(new Holiday("Labour Day",
                    "1 May 2021",
                    R.drawable.labour_day));
            holidays.add(new Holiday("National Day",
                    "9 Aug 2021",
                    R.drawable.national_day));
        } else if (holidayType.equals("Ethnic & Religion")) {
            holidays.add(new Holiday("Chinese New Year",
                    "12-13 Feb 2021",
                    R.drawable.cny));
            holidays.add(new Holiday("Good Friday",
                    "2 Apr 2021",
                    R.drawable.good_friday));
            holidays.add(new Holiday("Hari Raya Puasa",
                    "13 May 2021",
                    R.drawable.hari_raya_puasa));
            holidays.add(new Holiday("Vesak Day",
                    "26 May 2021",
                    R.drawable.vesak_day));
            holidays.add(new Holiday("Hari Raya Haji",
                    "20 Jul 2021",
                    R.drawable.hari_raya_haji));
            holidays.add(new Holiday("Deepavali",
                    "4 Nov 2021",
                    R.drawable.deepavali));
            holidays.add(new Holiday("Christmas Day",
                    "25 Dec 2021",
                    R.drawable.christmas));
        }

        adapter = new HolidayListAdapter(this, R.layout.holiday_row, holidays);

        holidayList.setAdapter(adapter);

        holidayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Holiday selectedHoliday = holidays.get(i);
                String toastMsg = selectedHoliday.getDayName() + " Date: " + selectedHoliday.getDate();
                Toast.makeText(HolidayListActivity.this, toastMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}