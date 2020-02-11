package com.example.lab2starter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

// 1. Import the correct Toolbar package
import androidx.appcompat.widget.Toolbar;

public class MovieDetailActivity extends AppCompatActivity {

    final String TAG = "CINEPLEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_movie_detail_screen));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Movie data
        String movieName = "Avengers: End Game";
        String genre = "Action";
        String releaseDate = "2019-5-23";

        // debug
        Log.d(TAG, "Name: " + movieName);
        Log.d(TAG, "Genre: " + genre);
        Log.d(TAG, "Release Date: " + releaseDate);

        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            // update ui
            TextView tvMovieName = (TextView) findViewById(R.id.tvMovieDetailName);
            TextView tvReleaseDate = (TextView) findViewById(R.id.tvMovieDetailReleaseDate);
            TextView tvGenre = (TextView) findViewById(R.id.tvMovieDetailGenre);

            tvMovieName.setText(bundle.getString("name"));
            tvGenre.setText(bundle.getString("genre"));
            tvReleaseDate.setText(bundle.getString("releaseDate"));
        }
    }

}
