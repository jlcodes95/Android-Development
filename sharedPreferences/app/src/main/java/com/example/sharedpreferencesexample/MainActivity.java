package com.example.sharedpreferencesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;

    public static final String SP_NAME = "SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void saveButtonPressed(View view){
        Log.d("saveButtonPressed", "clicking");
        String str = ((EditText) findViewById(R.id.editText)).getText().toString();
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString("data", str);
        spEditor.apply();
    }

    public void getDataButtonPressed(View view){
        Log.d("getDataButtonPressed", "clicking");
        ((TextView) findViewById(R.id.tvResult)).setText(sp.getString("data", ""));
    }
}
