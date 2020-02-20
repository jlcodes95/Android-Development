package com.example.tourismapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.tourismapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //setup toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.tbSettings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
