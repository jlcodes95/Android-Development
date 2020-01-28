package com.example.myfirstproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String USD = "USD";
    private static final String CAD = "CAD";
    private static final double USD_TO_CAD_RATE = 1.32;
    private static final double CAD_TO_USD_RATE = 0.76;

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void labelChange(View view){
        if (((Switch) findViewById(R.id.swU2C)).isChecked()) {
            ((TextView) findViewById(R.id.tvPrompt)).setText("Enter " + USD + " amount");
        }else{
            ((TextView) findViewById(R.id.tvPrompt)).setText("Enter " + CAD + " amount");
        }
    }

    public void convertButtonPressed(View view){
        try{
            //get data from edit text
            TextView amtTextBox = (TextView) findViewById(R.id.etAmount);

            //calculate
            double convertedAmt;
            String targetCountry;
            //USD TO CAD
            if (((Switch) findViewById(R.id.swU2C)).isChecked()){
                convertedAmt = Double.parseDouble(amtTextBox.getText().toString()) * USD_TO_CAD_RATE;
                targetCountry = CAD;
            }else{
                convertedAmt = Double.parseDouble(amtTextBox.getText().toString()) * CAD_TO_USD_RATE;
                targetCountry = USD;
            }


            //display result
            ((TextView) findViewById(R.id.tvResult)).setText("Converted " + targetCountry + " Amount is: $" + df.format(convertedAmt));
        }catch(Exception e){
            Toast t = Toast.makeText(this, "Please Only enter numbers...", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
