package com.example.finalteststarter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalteststarter.R;
import com.example.finalteststarter.classes.Donut;

import java.util.ArrayList;
import java.util.List;

public class DonutListViewAdapter extends ArrayAdapter<Donut> {

    private List<Donut> donuts;
    private Context context;
    private int resource;

    public DonutListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Donut> objects) {
        super(context, resource, objects);
        donuts = objects;
        this.context=context;
        this.resource=resource;
    }

    public DonutListViewAdapter(@NonNull Context context, int resource, @NonNull List<Donut> objects) {
        super(context, resource, objects);
        donuts = objects;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // need the icon, name, and now playing status
        Donut donut = donuts.get(position);

        String name = donut.getName();
        String description = donut.getDescription();
//        double price  = donut.getPrice();


        //Create an Layout inflater;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);


        //fetch text views
        TextView tvDonutName = (TextView) convertView.findViewById(R.id.tvDonutName);
        TextView tvDonutDescription = (TextView) convertView.findViewById(R.id.tvDonutDescription);
//        TextView tvDonutPrice = (TextView) convertView.findViewById(R.id.tvDonutPrice);


        //set text for each text views
        tvDonutName.setText(name);
        tvDonutDescription.setText(description);
//        tvDonutPrice.setText(""+price);

        return convertView;
    }
}
