package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView myListViewCategory;
    ArrayList<Category> myArrayListCategory = new ArrayList<>();
    ArrayAdapter<Category> myCategoryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListViewCategory = findViewById(R.id.lvCategories);
        myCategoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayListCategory);
        myListViewCategory.setAdapter(myCategoryArrayAdapter);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/C302_P05_Sakila/getCategories.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject myCategory = (JSONObject) response.get(i);
                        Category myCategoryItem = new Category
                                (
                                        myCategory.getInt("category_id"),
                                        myCategory.getString("name")
                                );
                        myArrayListCategory.add(myCategoryItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myCategoryArrayAdapter.notifyDataSetChanged();
            }
        });

        myListViewCategory.setOnItemClickListener((adapterView, view, i, l) -> {
            Category myCurrentCategory = myArrayListCategory.get(i);
            Intent myViewFilmsActivityIntent = new Intent(MainActivity.this, ViewFilmsActivity.class);
            myViewFilmsActivityIntent.putExtra("category", myCurrentCategory);
            startActivity(myViewFilmsActivityIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int myCurrentMenuItemId = item.getItemId();
        final int myViewCategorySummaryMenuItemId = R.id.ViewCategorySummary;
        final int mySearchRentalLocationMenuItemId = R.id.SearchRentalLocation;

        if (myCurrentMenuItemId == myViewCategorySummaryMenuItemId) {
            Intent myCategorySummaryActivityIntent = new Intent(MainActivity.this, CategorySummaryActivity.class);
            startActivity(myCategorySummaryActivityIntent);
            return true;
        } else if (myCurrentMenuItemId == mySearchRentalLocationMenuItemId) {
            Intent mySearchFilmsActivityIntent = new Intent(MainActivity.this, SearchFilmsActivity.class);
            startActivity(mySearchFilmsActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
