package com.example.mealdeliveryadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mealdeliveryadmin.R;

public class SigninActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_SIGNIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


    }

    public void onSigninPressed(View view){
        Log.d(TAG, "attempt to sign in");
    }
}
