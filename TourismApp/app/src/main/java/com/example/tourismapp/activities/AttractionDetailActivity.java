package com.example.tourismapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.example.tourismapp.R;
import com.example.tourismapp.components.Attraction;

public class AttractionDetailActivity extends AppCompatActivity {

    private Attraction attraction;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.tbAttractionDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null){
            attraction = (Attraction) intent.getSerializableExtra("attraction");
            username = intent.getStringExtra("username");
        }

    }
}
