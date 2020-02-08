package com.example.employeelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Person> employees = new ArrayList<>();
        Person p1 = new Person("First", "Person", "Female", "Testing", 10000);
        Person p2 = new Person("Second", "Person", "Male", "Feasting", 20020);
        Person p3 = new Person("Third", "Person", "Female", "Nesting", 33300);
        Person p4 = new Person("Fourth", "Person", "Male", "Guessing", 44444);
        Person p5 = new Person("Fifth", "Person", "Male", "Gusting", 55500);
        Person p6 = new Person("Sixth", "Person", "Female", "Besting", 60660);
        Person p7 = new Person("Seventh", "Person", "Female", "Resting", 70070);
        Person p8 = new Person("Eighth", "Person", "Male", "Setting", 88888);
        Person p9 = new Person("Ninth", "Person", "Female", "Jesting", 90099);
        Person p10 = new Person("Tenth", "Person", "Male", "Eating", 101010);
        employees.add(p1);
        employees.add(p2);
        employees.add(p3);
        employees.add(p4);
        employees.add(p5);
        employees.add(p6);
        employees.add(p7);
        employees.add(p8);
        employees.add(p9);
        employees.add(p10);

        PersonListViewAdapter adapter = new PersonListViewAdapter(this, R.layout.list_layout, employees);
        final ListView lv = findViewById(R.id.lvMain);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Person p = (Person) lv.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", p.getFirstName() + " " + p.getLastName());
                bundle.putString("department", p.getDepartment());
                bundle.putString("gender", p.getGender());
                bundle.putDouble("salary", p.getSalary());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
