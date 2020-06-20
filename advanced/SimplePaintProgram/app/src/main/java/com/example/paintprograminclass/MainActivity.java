package com.example.paintprograminclass;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    final String TAG="MAINPAINT";

    CustomDrawingSurface customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        this.customView
                = new CustomDrawingSurface(this);
        layout.addView(customView);


        setContentView(layout);


        // setContentView(customView);

    }


    public void eraseButtonPressed(View view) {
        Log.d(TAG, "Erase button pressed");
        this.customView.erase();
    }

    public void pinkButtonPressed(View view) {
        Log.d(TAG, "Pink button pressed");
        this.customView.changeColor(Color.argb(255, 227, 9, 176));

    }


}
