package com.example.samplecamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_EMPLOYEE";
    private EmployeeListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Intent i = getIntent();
        ArrayList<Employee> employees = (ArrayList<Employee>) i.getSerializableExtra("employees");
        for (Employee e: employees) {
            Log.d(TAG, e.getFirstName() + " " + e.getLastName() + " : " + e.getDateHired().toString());
        }

        this.adapter = new EmployeeListViewAdapter(this, R.layout.employee_layout, employees);
        final ListView mainView = (ListView) findViewById(R.id.mainListView);
        mainView.setAdapter(this.adapter);

        //onclicklistener??

    }
}
