package com.example.samplecamera;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmployeeListViewAdapter extends ArrayAdapter<Employee> {

    private ArrayList<Employee> employees;
    private Context context;
    private int resource;

    public EmployeeListViewAdapter(Context context, int resource, ArrayList<Employee> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.employees = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //create layout inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        tvName.setText(this.employees.get(position).getFirstName() + " " + this.employees.get(position).getLastName());
        tvName.setText(this.employees.get(position).getDateHired().toString());

        return convertView;
    }
}
