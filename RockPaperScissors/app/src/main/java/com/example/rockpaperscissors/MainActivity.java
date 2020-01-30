package com.example.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static HashMap<String, String> BEATS;
    private static final String ID_ROCK = "rock";
    private static final String ID_PAPER = "paper";
    private static final String ID_SCISSOR = "scissor";
    private static final int PROGRESS_INCREMENTATION = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BEATS = new HashMap<String, String>();
        BEATS.put(ID_ROCK, ID_SCISSOR);
        BEATS.put(ID_PAPER, ID_ROCK);
        BEATS.put(ID_SCISSOR, ID_PAPER);
    }

    public void onClickChangeSelection(View view){
        Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        String userChoice = view.getTag().toString();
        String cpuChoice = generateCpuChoice();
        Log.d("USER", userChoice);
        Log.d("CPU", cpuChoice);
        if (BEATS.get(userChoice).equals(cpuChoice)){
            //user beats cpu
            Log.d("RESULT", "user win");
            ((ProgressBar) findViewById(R.id.pbPlayer)).incrementProgressBy(PROGRESS_INCREMENTATION);
            t.setText("You won with " + userChoice + "!");
        }else if (BEATS.get(cpuChoice).equals(userChoice)){
            //cpu beats user
            Log.d("RESULT", "cpu win");
            ((ProgressBar) findViewById(R.id.pbCpu)).incrementProgressBy(PROGRESS_INCREMENTATION);
            t.setText("Cpu won with " + cpuChoice + "!");
        }else{
            //tie
            Log.d("RESULT", "a tie");
            t.setText("You tied with Cpu choosing " + cpuChoice + "!");
        }
        t.show();
    }

    private String generateCpuChoice(){
        Random random = new Random();
        String[] choices = {ID_ROCK, ID_PAPER, ID_SCISSOR};
        return choices[random.nextInt(3)];
    }

}
