package com.example.labtest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomCaseListAdapter extends ArrayAdapter<Case> {
    private Context context;
    private int resource;
    private ArrayList<Case> objects;

    public CustomCaseListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Case> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Case currentCase = this.objects.get(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView tvProvince = (TextView) convertView.findViewById(R.id.tvProvince);
        TextView tvTotalCases = (TextView) convertView.findViewById(R.id.tvTotalCases);
        TextView tvTotalRecovered = (TextView) convertView.findViewById(R.id.tvTotalRecovered);
        TextView tvTotalDeaths = (TextView) convertView.findViewById(R.id.tvTotalDeaths);

        tvProvince.setText("Province: " + currentCase.getProvince());
        tvTotalCases.setText("Total Cases: " + currentCase.getTotalCases());
        tvTotalRecovered.setText("Total Recovered: " + currentCase.getTotalRecovered());
        tvTotalDeaths.setText("Total Deaths: " + currentCase.getTotalDeaths());

        return convertView;
    }
}
