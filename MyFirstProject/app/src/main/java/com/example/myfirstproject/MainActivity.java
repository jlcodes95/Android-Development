package com.example.myfirstproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String USD_TO_CAD = "Enter USD Amount";
    private static final String CAD_TO_USD = "Enter CAD Amount";
    private static final double USD_TO_CAD_RATE = 1.25;
    private static final double CAD_TO_USD_RATE = 0.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convertButtonPressed(View view){
        try{
            //get data from edit text
            TextView amtTextBox = (TextView) findViewById(R.id.etAmount);

            //calculate
            double convertedAmt = Double.parseDouble(amtTextBox.getText().toString()) * 0.75;

            //display result
            ((TextView) findViewById(R.id.tvResult)).setText("Converted USD Amount is: $" + convertedAmt);
        }catch(Exception e){
            Toast t = Toast.makeText(this, "Please Only enter numbers...", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
