package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickGoToCalculate(View view){
        Log.d("onClickGoToCalculate", "Clicked button.");
        startActivity(new Intent(this, CalculateActivity.class));
    }

    public void onClickGoToConvert(View view){
        Log.d("onClickGoToConvert", "Clicked button.");
        startActivity(new Intent(this, ConvertActivity.class));
    }

}
