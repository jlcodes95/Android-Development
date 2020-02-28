package com.example.mymessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tbMain));

        Intent intent = getIntent();
        if (intent != null){
            username = intent.getStringExtra("username");
            ((TextView) findViewById(R.id.tvGreetings)).setText("Greetings, " + username + "!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.miLogout:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return true;
    }

    public void onClickShowTimePicker(View view){
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickShowDatePicker(View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public void onTimePickerSet(int hour, int minute){
       Log.d("TIME", hour + ":" + minute);
    }
    public void onDatePickerSet(int year, int month, int day){
        Log.d("TIME", year + "-" + month + "-" + day);
    }
}
