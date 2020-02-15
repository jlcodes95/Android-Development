package com.example.roomexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserArrayAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;
    private List<User> objects;


    public UserArrayAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = this.objects.get(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        ((TextView) convertView.findViewById(R.id.tvId)).setText("" + user.getId());
        ((TextView) convertView.findViewById(R.id.tvName)).setText(user.getFirstName() + " " + user.getLastName());
        ((TextView) convertView.findViewById(R.id.tvEmail)).setText(user.getEmail());

        return convertView;
    }
}
