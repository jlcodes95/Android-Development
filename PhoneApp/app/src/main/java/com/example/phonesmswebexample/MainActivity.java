package com.example.phonesmswebexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final String TAG="PHONE-EXAMPLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("MissingPermission")
    public void callPhoneButtonPressed(View view) {
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        String number = etPhoneNumber.getText().toString();

        Log.d(TAG, "Phone number is: " + number);

        String formattedPhoneNumber = "tel:" + number;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse(formattedPhoneNumber));

        if (i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d(TAG, "ERROR: cannot find app that matches ACTION_CALL intent");
        }
    }

    public void openDialScreenButtonPressed(View view) {
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        String number = etPhoneNumber.getText().toString();

        Log.d(TAG, "Phone number is: " + number);

        String formattedPhoneNumber = "tel:" + number;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse(formattedPhoneNumber));

        if (i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d(TAG, "ERROR: cannot find app that matches ACTION_DIALER intent");
        }
    }

    public void openSMSScreenButtonPressed(View view) {
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        String number= etPhoneNumber.getText().toString();
        EditText etMessage = findViewById(R.id.etMessage);
        String message = etMessage.getText().toString();

        Log.d(TAG, "Phone number is: " + number);

        String formattedPhoneNumber = "smsto:" + number;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse(formattedPhoneNumber));
        i.putExtra("sms_body", message);

        if (i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d(TAG, "ERROR: cannot find app that matches ACTION_SENDTO intent");
        }
    }

    public void sendSMSButtonPressed(View view) {
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        String number= etPhoneNumber.getText().toString();
        EditText etMessage = findViewById(R.id.etMessage);
        String message = etMessage.getText().toString();

        Log.d(TAG, "Phone number is: " + number);

        String formattedPhoneNumber = "smsto:" + number;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        String scAddress = null;
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, scAddress, message, sentIntent, deliveryIntent);
//
//
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setData(Uri.parse(formattedPhoneNumber));
//        i.putExtra("sms_body", message);
//
//        if (i.resolveActivity(getPackageManager()) != null){
//            startActivity(i);
//        }else{
//            Log.d(TAG, "ERROR: cannot find app that matches ACTION_SEND intent");
//        }
    }
    public void openWebpageButtonPressed(View view) {

    }



}
