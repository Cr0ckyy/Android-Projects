package com.myapplicationdev.android.c302_p13_ps_movie_db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    ListView movieListView;
    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    FirebaseFirestore fireStore;
    SharedPreferences sharedPreferences;
    Intent intent;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListView = findViewById(R.id.listViewMovies);

        //TODO: initialize ArrayList ,adapter,then ListView
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, R.layout.movie_row, movies);
        movieListView.setAdapter(movieAdapter);

        sharedPreferences = getSharedPreferences("C302_P13", Context.MODE_PRIVATE);

        // TODO: getting fireStore collection References
        fireStore = FirebaseFirestore.getInstance();
        collectionReference = fireStore.collection("movies");

        // TODO: adding data into fireStore
        collectionReference.addSnapshotListener((QuerySnapshot value, FirebaseFirestoreException e) -> {

            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            // clear data in Movie ArrayList to enable data reload
            movies.clear();

            // loop through firebase documents to retrieve data and add it to a ListView
            for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                Movie movie = doc.toObject(Movie.class);
                movie.setMovieId(doc.getId());
                movies.add(movie);
            }

            // reload adapter thus refresh listview data
            movieAdapter.notifyDataSetChanged();
        });

        movieListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            // Get the selected Movie data
            Movie selectedMovie = movies.get(position);
            String selectedMovieID = selectedMovie.getMovieId();

            // pass selected Inventory's MovieId data to EditMovieActivity
            intent = new Intent(getBaseContext(), EditMovieActivity.class);
            intent.putExtra("movie_id", selectedMovieID);
            startActivity(intent);

        });

    }

    @Override
    // TODO: Dispatch onResume() to fragments to resume data loading
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getString("apikey", "").isEmpty()) {
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();


        if (itemId == R.id.menu_add) {
            Intent intent = new Intent(getApplicationContext(), CreateMovieActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}