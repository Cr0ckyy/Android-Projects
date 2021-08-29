package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CategorySummaryActivity extends AppCompatActivity {

    ListView mySummaryListView;
    ArrayList<CategorySummary> myCategorySummaryArrayList;
    SummaryAdapter mySummaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_summary);

        mySummaryListView = findViewById(R.id.lvSummary);
        myCategorySummaryArrayList = new ArrayList<>();
        mySummaryAdapter = new SummaryAdapter(this, R.layout.activity_summary_list, myCategorySummaryArrayList);
        mySummaryListView.setAdapter(mySummaryAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/C302_P05_Sakila/getCategorySummary.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject CategorySummarbject = (JSONObject) response.get(i);

                        CategorySummary myCategorySummary = new CategorySummary(CategorySummarbject.
                                getString("category"),
                                CategorySummarbject.getInt("number_films"));

                        myCategorySummaryArrayList.add(myCategorySummary);
                    }
                    mySummaryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}