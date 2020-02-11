package com.example.lab2starter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Movie {

    // You may modify this class as you see fit.
    private String name;
    private String genre;
    private String releaseDate;

    public Movie(String name, int year, int month, int day, String genre) {
        this.name = name;
        this.genre = genre;
        this.releaseDate = year + "-" + month + "-" + day;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isMoviePlaying() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date movieDate = sdf.parse(releaseDate);
        Date currentDate = new Date();
        Log.d("MOVIE_DATE", movieDate.toString());
        Log.d("CURRENT_DATE", currentDate.toString());
        return currentDate.after(movieDate);
    }

    public boolean isGenre(String genre){
        return this.genre.toLowerCase().contains(genre.toLowerCase());
    }
}

