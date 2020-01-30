package com.example.simplemultiscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        String name = ((EditText) findViewById(R.id.etName)).getText().toString();
        String message = ((EditText) findViewById(R.id.etMessage)).getText().toString();


        //radio.isChecked()

        boolean toCall = ((RadioButton) findViewById(R.id.rbYes)).isChecked();

        Intent messageIntent = new Intent(this, SubmittedActivity.class);
//        messageIntent.putExtra("name", name);
//        messageIntent.putExtra("message", message);

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        if (toCall){
            bundle.putString("message", "As you indicated, we will call you");
        }else{
            bundle.putString("message", "As you indicated, we will not call you");
        }
        messageIntent.putExtras(bundle);
        startActivity(messageIntent);
    }
}
