package com.example.passingobjectsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ArrayList<Student> students = (ArrayList<Student>) getIntent().getSerializableExtra("student");
        String text = "";
        for (int i = 0; i < students.size(); i++){
            text += "Student Name: " + students.get(i).getName() +
                    "\n Student ID: " + students.get(i).getStudentId() +
                    "\n Student GPA: " + students.get(i).getGpa();
        }
        ((TextView) findViewById(R.id.tvResults)).setText(text);
    }
}
