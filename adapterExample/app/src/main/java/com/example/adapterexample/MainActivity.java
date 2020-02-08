package com.example.adapterexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    // data source (grey box)
    String countryNames[] = {"Canada", "USA","Mexico", "Belize", "Guatemala", "Honduras"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the drop down
        Spinner countryDropDown = (Spinner) findViewById(R.id.countries_spinner);

        // 1. make an "adapater" (make the translator)
        // 2. connect the translator to the spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                countryNames);


        countryDropDown.setAdapter(adapter);
    }

    public void buttonPressed(View view) {
        Intent i = new Intent(this, DropDown.class);
        startActivity(i);
    }
}
