package com.example.labtest1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final String TAG = "DEBUG_MATCHESADAPTER";
    private ArrayList<Person> matches;
    IOnRowClickedListener listenerInterface;

    public MatchesAdapter(ArrayList<Person> matches, IOnRowClickedListener listener) {
        this.matches = matches;
        this.listenerInterface = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // get the custom row layout
        View personRowView = inflater.inflate(R.layout.person_row_layout, parent, false);

        // Return the view holder
        ViewHolder holder = new ViewHolder(personRowView, this.listenerInterface);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item from list
        Person person = matches.get(position);

        //display item in recyclerView
//        int id = holder.itemView.getResources().getIdentifier(pokemon.getImg_path(), "drawable", "com.example.recyclerview");
        TextView tvFullName = holder.getTvFullName();
        TextView tvCoordinates = holder.getTvCoordinates();

        Log.d(TAG, ""+position);
        tvFullName.setText(person.getFullName());
        tvCoordinates.setText("Located: " + person.getCoordinates().toString());
    }

    @Override
    public int getItemCount() {
        return this.matches.size();
    }

}
