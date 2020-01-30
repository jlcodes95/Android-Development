package com.example.simplemultiscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SubmittedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ((TextView) findViewById(R.id.tvHeadingInfo)).setText(bundle.getString("name"));
        ((TextView) findViewById(R.id.tvCallInfo)).setText(bundle.getString("message"));
    }

}
