package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IOnRowClickedListener {

    private ArrayList<Pokemon> pokemonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.createDataSet();
        this.recyclerView = (RecyclerView) findViewById(R.id.rvPokemonList);
        this.adapter = new Adapter(this.pokemonList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createDataSet() {
        this.pokemonList.clear();
        this.pokemonList.add(new Pokemon("Abra", "abra"));
        this.pokemonList.add(new Pokemon("Bellsprout", "bellsprout"));
        this.pokemonList.add(new Pokemon("Caterpie", "caterpie"));
        this.pokemonList.add(new Pokemon("Charmander", "charmander"));
        this.pokemonList.add(new Pokemon("Dratini", "dratini"));
        this.pokemonList.add(new Pokemon("Mew", "mew"));
    }

    @Override
    public void onRowClicked(int position) {
        Log.d("DBUG", "Pokemon name is: " + this.pokemonList.get(position).getName());
    }
}
