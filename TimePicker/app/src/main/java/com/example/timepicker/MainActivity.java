package com.example.timepicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_TIME_PICKER = "TIME PICKER";
    private static final String TAG_DATE_PICKER = "DATE PICKER";
    private static final String TAG_DIALOG = "DIALOG";
    private static TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
    }

    public void onClickTimePicker(View view){
        Log.d(TAG_TIME_PICKER, "working");
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickDatePicker(View view){
        Log.d(TAG_DATE_PICKER, "working");
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickDialog(View view){
        Log.d(TAG_DIALOG, "working");
        AlertDialog.Builder popupBox = new AlertDialog.Builder(this);
        popupBox.setTitle("new popup");
        popupBox.setMessage("NEW MESSAGE!!!");
        popupBox.setPositiveButton("taco", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast t = Toast.makeText(getApplicationContext(), "you've clicked the taco button!", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        popupBox.setNegativeButton("burrito", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast t = Toast.makeText(getApplicationContext(), "you've clicked the burrito button!", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        popupBox.setNeutralButton("chinese", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast t = Toast.makeText(getApplicationContext(), "you've clicked the chinese button!", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        popupBox.show();
//        new AlertDialog
//                .Builder(this)
//                .setTitle("custom Dialog")
//                .setMessage("a custom message")
//                .setPositiveButton(android.R.string.yes, null)
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
    }

    public void showTimeResult(int hour, int minute){
        String text = String.format("The time is %02d:%02d", hour, minute);
        tvResult.setText(text);
    }

    public void showDateResult(int year, int month, int day){
        String text = String.format("The date is %02d-%02d-%02d", month, day, year);
        tvResult.setText(text);
    }
}
