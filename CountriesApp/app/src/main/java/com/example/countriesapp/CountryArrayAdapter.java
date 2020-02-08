package com.example.countriesapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CountryArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Country> objects;
    private HashMap<String, Integer> flagMap;

    public CountryArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Country> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        flagMap = new HashMap<String, Integer>();
        flagMap.put("United States", R.drawable.united_states);
        flagMap.put("Mexico", R.drawable.mexico);
        flagMap.put("Canada", R.drawable.canada);
        flagMap.put("Honduras", R.drawable.honduras);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Country country = objects.get(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);
        ((TextView) convertView.findViewById(R.id.tvName)).setText(country.getCountryName());
        ((TextView) convertView.findViewById(R.id.tvPopulation)).setText("Population: " + country.getPopulation());
        ((ImageView) convertView.findViewById(R.id.ivFlag)).setImageResource(flagMap.get(country.getCountryName()));

        return convertView;
    }
}
