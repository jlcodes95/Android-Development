package com.example.recyclerview;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Pokemon> pokemonList;

    IOnRowClickedListener listenerInterface;

    public Adapter(ArrayList<Pokemon> pokemonList, IOnRowClickedListener listener) {
        this.pokemonList = pokemonList;
        this.listenerInterface = listener;
    }

    @Override
    public int getItemCount() {
        return this.pokemonList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item from list
        Pokemon pokemon = pokemonList.get(position);

        //display item in recyclerView
        int id = holder.itemView.getResources().getIdentifier(pokemon.getImg_path(), "drawable", "com.example.recyclerview");
        ImageView image = holder.getImage();
        TextView name = holder.getName();
        image.setImageResource(id);
        name.setText(pokemon.getName());
//        holder.name.setText(pokemon.getName());
//        holder.image.setImageResource(id);
//        Log.d("123AD", pokemon.getName() + id);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // get the custom row layout
        View pokemonRowView = inflater.inflate(R.layout.row_layout, parent, false);

        // Return the view holder
        ViewHolder holder = new ViewHolder(pokemonRowView, this.listenerInterface);
        return holder;
    }
}
