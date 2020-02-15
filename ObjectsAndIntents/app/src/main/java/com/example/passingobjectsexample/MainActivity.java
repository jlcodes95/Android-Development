package com.example.passingobjectsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendStudentPressed(View view) {
        // get studnet info
        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etId = (EditText) findViewById(R.id.etId);
        EditText etGPA = (EditText) findViewById(R.id.etGPA);

        String name = etName.getText().toString();
        long studentId = Long.valueOf(etId.getText().toString());
        double gpa = Double.valueOf(etGPA.getText().toString());


        // @TODO:  Create a new student
        Student student = new Student(name, studentId, gpa);
        Student student2 = new Student(name + "2", studentId, gpa);
        Student student3 = new Student(name + "3", studentId, gpa);
        Student student4 = new Student(name + "4", studentId, gpa);
        ArrayList<Student> list = new ArrayList<Student>();
        list.add(student);
        list.add(student2);
        list.add(student3);
        list.add(student4);


        // @TODO:  Send that student to the next screen
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("student", list);
        startActivity(intent);

    }
}
