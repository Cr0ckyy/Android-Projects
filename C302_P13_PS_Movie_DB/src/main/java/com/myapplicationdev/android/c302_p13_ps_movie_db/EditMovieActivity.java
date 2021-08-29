package com.myapplicationdev.android.c302_p13_ps_movie_db;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditMovieActivity extends AppCompatActivity {

    static final String TAG = "EditMovieActivity";
    EditText etTitle, etRated, etReleased, etRuntime, etGenre, etActors, etPlot, etLanguage, etPoster;
    Button btnUpdate, btnDelete;
    String movieId;
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        etTitle = findViewById(R.id.etTitle);
        etRated = findViewById(R.id.etRated);
        etReleased = findViewById(R.id.etReleased);
        etRuntime = findViewById(R.id.etRuntime);
        etGenre = findViewById(R.id.etGenre);
        etActors = findViewById(R.id.etActors);
        etPlot = findViewById(R.id.etPlot);
        etLanguage = findViewById(R.id.etLanguage);
        etPoster = findViewById(R.id.etPoster);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("movie_id");
        documentReference = fireStore.collection("movies").document(movieId);
        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }

            if (value != null && value.exists()) {
                Movie movie = value.toObject(Movie.class);
                String title = movie.getTitle();
                String rated = movie.getRating();
                String released = movie.getReleased();
                String runtime = movie.getRuntime();
                String genre = movie.getGenre();
                String actors = movie.getActors();
                String plot = movie.getPlot();
                String lang = movie.getLanguage();
                String poster = movie.getPoster();


                etTitle.setText(title);
                etRated.setText(rated);
                etReleased.setText(released);
                etRuntime.setText(runtime);
                etGenre.setText(genre);
                etActors.setText(actors);
                etPlot.setText(plot);
                etLanguage.setText(lang);
                etPoster.setText(poster);

            }
        });
//        documentReference.get().addOnCompleteListener((Task<DocumentSnapshot> task) -> {
//            if (task.isSuccessful()) {
//
//                DocumentSnapshot document = task.getResult();
//
//                if (document.exists()) {
//                    etTitle.setText(document.getString("Title"));
//                    etRated.setText(document.getString("Rated"));
//                    etReleased.setText(document.getString("Released"));
//                    etRuntime.setText(document.getString("Runtime"));
//                    etGenre.setText(document.getString("Genre"));
//                    etActors.setText(document.getString("Actors"));
//                    etPlot.setText(document.getString("Plot"));
//                    etLanguage.setText(document.getString("Language"));
//                    etPoster.setText(document.getString("Poster"));
//                } else {
//                    Log.d(TAG, "No such document");
//                }
//            } else {
//                Log.e(TAG, "get failed with ", task.getException());
//            }
//        });

        btnUpdate.setOnClickListener(this::updateMovie);

        btnDelete.setOnClickListener(this::deleteMovie);
    }


    public void updateMovie(View v) {

        Movie movie = new Movie(
                etTitle.getText().toString().trim(),
                etRated.getText().toString().trim(),
                etReleased.getText().toString().trim(),
                etRuntime.getText().toString().trim(),
                etGenre.getText().toString().trim(),
                etActors.getText().toString().trim(),
                etPlot.getText().toString().trim(),
                etLanguage.getText().toString().trim(),
                etPoster.getText().toString().trim()
        );

        fireStore.collection("movies").document(movieId).set(movie)
                .addOnSuccessListener((Void unused) ->
                        Toast.makeText(this, "This movie has been updated.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener((Exception e) ->
                        Toast.makeText(this, "Fail to update this movie.", Toast.LENGTH_SHORT).show()
                );

        finish();
    }

    public void deleteMovie(View v) {
        fireStore.collection("movies").document(movieId).delete()
                .addOnSuccessListener((Void unused) ->
                        Toast.makeText(this, "This movie has been deleted.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener((Exception e) ->
                        Toast.makeText(this, "Fail to delete this movie.", Toast.LENGTH_SHORT).show());
        finish();
    }

}