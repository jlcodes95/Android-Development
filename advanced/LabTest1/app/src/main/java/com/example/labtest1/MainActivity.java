package com.example.labtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    public final static String CRITERIA_MALE = "male";
    public final static String CRITERIA_FEMALE = "female";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMalePressed(View view) {
        Log.d(TAG, "male button pressed");
        redirectMatch(CRITERIA_MALE);
    }

    public void onFemalePressed(View view) {
        Log.d(TAG, "female button pressed");
        redirectMatch(CRITERIA_FEMALE);
    }

//    public void onAnyPressed(View view) {
//        Log.d(TAG, "any button pressed");
//        redirectMatch("");
//    }

    private void redirectMatch(String criteria) {
        Intent intent = new Intent(this, MatchesActivity.class);
        intent.putExtra("criteria", criteria);
        startActivity(intent);
    }
}
