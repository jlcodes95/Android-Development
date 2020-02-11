package com.example.lab2starter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

// 1. Import the correct Toolbar package
import androidx.appcompat.widget.Toolbar;

public class MovieListActivity extends AppCompatActivity {

    final String TAG = "CINEPLEX";

    // data source
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ListView movieListView;

    // Shared Preferences
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "CineplexSP";
    private static String username;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // -------------------------------------
        // Initialize Shared Preferences
        // --------------------------------------

        // @TODO: Write code to initialize shared preferences
        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        // --------------------------------------
        // @HINT:  Get today's calendar date
        // --------------------------------------
//        Calendar calendar = Calendar.getInstance();
//        int currentYear = calendar.get(Calendar.YEAR);
//        int currentMonth = calendar.get(Calendar.MONTH);
//        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        String date = currentYear + "-" + currentMonth + "-" + currentDay;
//        Log.d(TAG, "Today's date is: " + date);

//        TextView tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);

//        tvCurrentDate.setText("Today's date is: " + date);

        //toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_movie_list_screen));

        //get intent
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                username = bundle.getString("username");
                password = bundle.getString("password");
            }
        }

        Log.d("MovieList", username);
        Log.d("MovieList", password);


        // --------------------------------------
        // Make some movies and add them to the movies list
        // - You can modify this code as you see fit
        // --------------------------------------
        Movie m1 = new Movie("Star Wars: The Rise of Skywalker", 2019, 1, 1, "Sci Fi and Fantasy");
        Movie m2 = new Movie("Jumanji: The Next Level", 2020, 2, 8, "Action");
        Movie m3 = new Movie("The LoveBirds", 2020,3,25, "Comedy");
        Movie m4 = new Movie("Fast and Furious 9", 2020, 4,17, "Action");

        movies.add(m1);
        movies.add(m2);
        movies.add(m3);
        movies.add(m4);

        // DEBUG: print out the movies to the terminal
        Log.d(TAG, "The movies in the system are:");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            Log.d(TAG, i + ": " + m.getReleaseDate());
        }
        Log.d(TAG, "-----");


        // --------------------------------------
        // ListView & Adapter Code
        // --------------------------------------

        //@TODO: Code for your listview and adapaters
        movieListView = findViewById(R.id.lvMovies);

        MovieListViewAdapter adapter = new MovieListViewAdapter(
                this,
                R.layout.movies_list_row_layout,
                movies
        );
        movieListView.setAdapter(adapter);
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 Movie m = (Movie) movieListView.getItemAtPosition(position);
                 Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putString("name", m.getName());
                 bundle.putString("genre", m.getGenre());
                 bundle.putString("releaseDate", m.getReleaseDate());
                 intent.putExtras(bundle);
                 startActivity(intent);
             }
         });

        // HINT: If you want to segue to another screen inside an onClick, you need code like this:
//        Intent i = new Intent(MovieListActivity.this, MovieDetailActivity.class);
//        startActivity(i);


    }

    public void movieDetailButtonPressed(View view) {
        Intent i = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        startActivity(i);
    }


    // Helper function to get all movie in a specific genre
    public ArrayList<Movie> getMoviesByGenre(String genre) {
        ArrayList<Movie> actionMovies = new ArrayList<Movie>();

        final String SEARCH_GENRE = genre;
        for (int i = 0; i < movies.size(); i++) {
            // @TODO: Write the code to search for action movies and add them to the actionMoviesList
            if (movies.get(i).isGenre(genre)){
                Log.d("IS_GENRE", movies.get(i).getName());
                actionMovies.add(movies.get(i));
            }
        }

        return actionMovies;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.miVAM:
                //view action movies
                Log.d("VIEW_ACTION_MOVIES", "TODO HERE");
                filterActionMovies();
                break;
            case R.id.miLogout:
                logout();
                break;
        }

        return true;
    }

    private void filterActionMovies(){
        ArrayList<Movie> actionMovies = getMoviesByGenre("Action");
        MovieListViewAdapter adapter = (MovieListViewAdapter) movieListView.getAdapter();
        adapter.clear();
        adapter.addAll(actionMovies);
        adapter.notifyDataSetChanged();

    }


    private void logout(){
        AlertDialog.Builder popupBox = new AlertDialog.Builder(this);
        popupBox.setTitle("Remember for next time?");
        popupBox.setMessage("Let us make login easier for you. By selecting AGREE, we will remember your login details, so you won't have to retype them next time.");
        popupBox.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("LOGOUT_AGREE", "clicked AGREE.");
                SharedPreferences.Editor spEditor = prefs.edit();
                spEditor.putString("username", username);
                spEditor.putString("password", password);
                spEditor.apply();
                startActivity(new Intent(MovieListActivity.this, LoginActivity.class));
            }
        });

        popupBox.setNegativeButton("DISAGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("LOGOUT_DISAGREE", "clicked DISAGREE.");
                SharedPreferences.Editor spEditor = prefs.edit();
                spEditor.clear();
                spEditor.apply();
                startActivity(new Intent(MovieListActivity.this, LoginActivity.class));
            }
        });
        popupBox.show();
    }

}
