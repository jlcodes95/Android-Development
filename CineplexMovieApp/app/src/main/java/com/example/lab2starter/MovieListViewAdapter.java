package com.example.lab2starter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieListViewAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private int resource;
    private ArrayList<Movie> objects;

    public MovieListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = this.objects.get(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        ((TextView) convertView.findViewById(R.id.tvRowMovieName)).setText(movie.getName());
        TextView status = convertView.findViewById(R.id.tvRowMovieStatus);
        ImageView icon = convertView.findViewById(R.id.ivRowMovieStatusIcon);
        try{
            if (movie.isMoviePlaying()){
                icon.setImageResource(R.drawable.now_playing);
                status.setText("Now Playing");
            }else{
                icon.setImageResource(R.drawable.coming_soon);
                status.setText("Release Date: " + movie.getReleaseDate());
            }
        }catch (Exception e){
            Toast t = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }

        return convertView;

    }
}
