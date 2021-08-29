package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewFilmsActivity extends AppCompatActivity {

    ListView myListView;
    ArrayList<Film> myFilmArrayList;
    FilmAdapter myFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        myListView = findViewById(R.id.lvFilms);
        myFilmArrayList = new ArrayList<>();
        myFilmAdapter = new FilmAdapter(this, R.layout.activity_film_list, myFilmArrayList);
        myListView.setAdapter(myFilmAdapter);

        Intent i = getIntent();
        Category c = (Category) i.getSerializableExtra("category");

        RequestParams params = new RequestParams("id", c.getId());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/C302_P05_Sakila/getmyFilmArrayListByCategoryId.php?id=1", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject myFilmJSONObject = (JSONObject) response.get(i);

                        Film film = new Film(
                                myFilmJSONObject.getString("title"),
                                myFilmJSONObject.getString("description"),
                                myFilmJSONObject.getInt("release_year"),
                                myFilmJSONObject.getString("rating")
                        );

                        myFilmArrayList.add(film);
                    }
                    myFilmAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}