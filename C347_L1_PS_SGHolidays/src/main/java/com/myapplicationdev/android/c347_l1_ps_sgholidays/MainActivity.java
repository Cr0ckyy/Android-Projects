package com.myapplicationdev.android.c347_l1_ps_sgholidays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView holidayTypeList;
    ArrayAdapter adapter;
    ArrayList<String> holidayTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holidayTypeList = findViewById(R.id.holidayTypeList);

        holidayTypes = new ArrayList<>();
        holidayTypes.add("Secular");
        holidayTypes.add("Ethnic & Religion");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, holidayTypes);

        holidayTypeList.setAdapter(adapter);

        holidayTypeList.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            Intent intent = new Intent(MainActivity.this, HolidayListActivity.class);
            intent.putExtra("holidayType", holidayTypes.get(i));
            startActivity(intent);
        });
    }
}