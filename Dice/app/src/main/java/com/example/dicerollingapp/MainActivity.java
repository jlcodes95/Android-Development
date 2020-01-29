package com.example.dicerollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //@TODO: Fill in your code here
    public void onClickRoll(View view){
        Random random = new Random();
        int i = random.nextInt(6) + 1;
        switch(i) {
            case 1:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice1);
                break;
            case 2:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice2);
                break;
            case 3:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice3);
                break;
            case 4:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice4);
                break;
            case 5:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice5);
                break;
            case 6:
                ((ImageView) findViewById(R.id.ivDice)).setImageResource(R.drawable.dice6);
                break;
        }

    }
}
