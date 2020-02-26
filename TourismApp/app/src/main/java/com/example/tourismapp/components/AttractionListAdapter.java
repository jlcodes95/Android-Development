package com.example.tourismapp.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourismapp.R;

import org.w3c.dom.Attr;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AttractionListAdapter extends ArrayAdapter<Attraction> {

    private Context context;
    private int resource;
    private List<Attraction> attractions;

    public AttractionListAdapter(@NonNull Context context, int resource, @NonNull List<Attraction> attractions) {
        super(context, resource, attractions);
        this.context = context;
        this.resource = resource;
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Attraction attraction = this.attractions.get(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        ((ImageView) convertView.findViewById(R.id.ivIcon)).setImageResource(context.getResources().getIdentifier(attraction.getImagePath(), "drawable", context.getPackageName()));
        ((TextView) convertView.findViewById(R.id.tvName)).setText(attraction.getName());
        ((TextView) convertView.findViewById(R.id.tvAddress)).setText(attraction.getAddress());
        ((TextView) convertView.findViewById(R.id.tvDescription)).setText(attraction.getShortenedDescription());

        return convertView;
    }

}
