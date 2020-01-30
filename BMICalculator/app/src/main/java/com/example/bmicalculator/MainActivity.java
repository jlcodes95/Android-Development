package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCalculate(View view){
        try{
            String wString = ((EditText) findViewById(R.id.etWeight)).getText().toString();
            String hString = ((EditText) findViewById(R.id.etHeight)).getText().toString();
            if (wString.equals("")){
                throw new Exception("weight cannot be less than 0");
            }else if (hString.equals("")){
                throw new Exception("height cannot be less than 0");
            }
            double weight = Double.parseDouble(wString);
            double height = Double.parseDouble(hString);

            if (height <= 0){
                throw new Exception("height cannot be less than 0");
            }

            double result = weight / (height * height);

            if (result < 18.5){
                ((TextView) findViewById(R.id.tvResult)).setText("You are underweight");
            }else if (result < 25){
                ((TextView) findViewById(R.id.tvResult)).setText("Your weight is normal");
            }else if (result < 30){
                ((TextView) findViewById(R.id.tvResult)).setText("You are overweight");
            }else{
                ((TextView) findViewById(R.id.tvResult)).setText("You are obese");
            }
        }catch (Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }

}

