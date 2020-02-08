package com.example.employeelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PersonListViewAdapter extends ArrayAdapter<Person> {

    private ArrayList<Person> persons;
    private Context context;
    private int resource;

    public PersonListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects) {
        super(context, resource, objects);
        this.persons = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Person p = this.persons.get(position);
        String name = p.getFirstName() + " " + p.getLastName();
        String department = p.getDepartment();
        String gender = p.getGender();
        String salary = "$"+p.getSalary();
        //create layout inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        //fetch text views && set text
        ((TextView) convertView.findViewById(R.id.tvName)).setText(name);
        ((TextView) convertView.findViewById(R.id.tvDepartment)).setText(department);
        ((TextView) convertView.findViewById(R.id.tvGender)).setText(gender);
        ((TextView) convertView.findViewById(R.id.tvSalary)).setText(salary);

        return convertView;
    }
}
