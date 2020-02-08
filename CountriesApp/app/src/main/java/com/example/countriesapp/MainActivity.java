package com.example.countriesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Country> countries= new ArrayList<Country>();
        countries.add(new Country("Canada", "Ottawa", 37279800));
        countries.add(new Country("United States", "Washington, DC", 327929000));
        countries.add(new Country("Mexico", "Mexico City", 132328789));
        countries.add(new Country("Honduras", "Tegucigalpa", 9012229));

//        countries.sort(Country.CountryComparator);

        CountryArrayAdapter adapter = new CountryArrayAdapter(this, R.layout.country_layout, countries);

        final ListView mainView = findViewById(R.id.listview_countries);
        adapter.sort(Country.CountryComparator);
        mainView.setAdapter(adapter);

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Country country = (Country) mainView.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("country", country.getCountryName());
                        intent.putExtra("capital", country.getCapital());
                        startActivity(intent);
                    }
                }
        );

    }

}
