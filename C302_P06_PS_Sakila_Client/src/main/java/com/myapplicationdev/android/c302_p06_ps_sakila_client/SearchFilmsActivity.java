package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchFilmsActivity extends AppCompatActivity {

    SearchView mySearchView;
    TextView myTextViewTitle, myTextViewDescription, myTextViewYear, myTextViewRating;
    ListView myListViewSearch;
    ArrayList<FilmLocation> myFilmLocationArrayList;
    FilmLocationAdapter myFilmLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_films);

        mySearchView = findViewById(R.id.sv);
        myTextViewTitle = findViewById(R.id.tvSearchTitle);
        myTextViewDescription = findViewById(R.id.tvSearchDescription);
        myTextViewYear = findViewById(R.id.tvSearchYear);
        myTextViewRating = findViewById(R.id.tvSearchRating);
        myListViewSearch = findViewById(R.id.lvSearch);


        myFilmLocationArrayList = new ArrayList<>();
        myFilmLocationAdapter = new FilmLocationAdapter(this, R.layout.activity_search_list, myFilmLocationArrayList);
        myListViewSearch.setAdapter(myFilmLocationAdapter);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                RequestParams myRequestParams = new RequestParams("id", newText);
                AsyncHttpClient myAsyncHttpClient = new AsyncHttpClient();
                myAsyncHttpClient.get("http://10.0.2.2/C302_P05_Sakila/getRentalLocationsByFilmId.php", myRequestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Film myFilm = new Film(
                                    response.getString("title"),
                                    response.getString("description"),
                                    response.getInt("release_year"),
                                    response.getString("rating")
                            );

                            JSONArray locations = (JSONArray) response.get("stores");

                            for (int i = 0; i < locations.length(); i++) {
                                JSONObject myFilmJSONObject = (JSONObject) locations.get(i);

                                FilmLocation myFilmLocation = new FilmLocation(
                                        myFilmJSONObject.getString("address"),
                                        myFilmJSONObject.getInt("postal_code"),
                                        myFilmJSONObject.getInt("phone"),
                                        myFilmJSONObject.getString("city"),
                                        myFilmJSONObject.getString("country"));

                                myFilmLocationArrayList.add(myFilmLocation);
                            }

                            myTextViewTitle.setText(myFilm.getTitle());
                            myTextViewDescription.setText(myFilm.getDescription());
                            myTextViewYear.setText(String.valueOf(myFilm.getRelease_year()));
                            myTextViewRating.setText(myFilm.getRating());


                            myFilmLocationAdapter.notifyDataSetChanged();
                        } catch (JSONException ignored) {
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestParams params = new RequestParams("id", query);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://10.0.2.2/C302_P05_Sakila/getRentalLocationsByFilmId.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Film myFilm = new Film(
                                    response.getString("title"),
                                    response.getString("description"),
                                    response.getInt("release_year"),
                                    response.getString("rating"));

                            JSONArray myFilmLocations = (JSONArray) response.get("stores");

                            for (int i = 0; i < myFilmLocations.length(); i++) {
                                JSONObject myFilmJSONObject = (JSONObject) myFilmLocations.get(i);

                                FilmLocation myFilmLocation = new FilmLocation(
                                        myFilmJSONObject.getString("address"),
                                        myFilmJSONObject.getInt("postal_code"),
                                        myFilmJSONObject.getInt("phone"),
                                        myFilmJSONObject.getString("city"),
                                        myFilmJSONObject.getString("country"));


                                myFilmLocationArrayList.add(myFilmLocation);
                            }

                            myTextViewTitle.setText(myFilm.getTitle());
                            myTextViewDescription.setText(myFilm.getDescription());
                            myTextViewYear.setText(String.valueOf(myFilm.getRelease_year()));
                            myTextViewRating.setText(myFilm.getRating());
                            myFilmLocationAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return false;
            }
        });

    }
}