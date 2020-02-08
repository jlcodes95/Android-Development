package com.example.adapterexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class DropDown extends AppCompatActivity {

    String countryNames[] = {"Canada", "USA","Mexico", "Belize", "Guatemala", "Honduras"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down);


        // initialize the drop down
        ListView lvCountry= findViewById(R.id.lvCountries);

        // 1. make an "adapater" (make the translator)
        // 2. connect the translator to the spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                countryNames);
        lvCountry.setAdapter(adapter);
    }
}
