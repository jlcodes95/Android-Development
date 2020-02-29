package com.example.tourismapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismapp.R;

public class ContactUsActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_PHONE = 0;
    private static final int MY_PERMISSIONS_REQUEST_SMS = 1;
    private static final String MSG_PHONE = "We require the `phone call` permission in order to call our support line.";
    private static final String MSG_SMS = "We require the `SMS` permission in order to send SMS messages.";
    private static final String MSG_DENIED = "Request denied, we do not have the required permission to perform this action.";
    private static final String TAG = "CONTACT_US";
    private static final String DEFAULT_PHONE_NUMBER = "4165559999";
    private static final String DEFAULT_SMS_MESSAGE = "Hello, I am inquiring about...";
    private static final String DEFAULT_EMAIL = "support@tourismapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //setup toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.tbContactUs));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickOpenCall(View view){
        checkPermission(Manifest.permission.CALL_PHONE, MY_PERMISSIONS_REQUEST_PHONE, MSG_PHONE);
    }

    public void onClickOpenSMS(View view){
        checkPermission(Manifest.permission.CALL_PHONE, MY_PERMISSIONS_REQUEST_SMS, MSG_SMS);
    }

    private void handleOpenCall(){

        String formattedPhoneNumber = "tel:" + DEFAULT_PHONE_NUMBER;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse(formattedPhoneNumber));

        if (i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d(TAG, "ERROR: cannot find app that matches ACTION_DIALER intent");
        }
    }

    private void handleOpenSMS(){

        String formattedPhoneNumber = "smsto:" + DEFAULT_PHONE_NUMBER;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse(formattedPhoneNumber));
        i.putExtra("sms_body", DEFAULT_SMS_MESSAGE);

        if (i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d(TAG, "ERROR: cannot find app that matches ACTION_SENDTO intent");
        }
    }

    public void onClickOpenEmail(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + DEFAULT_EMAIL);
        intent.setData(data);
        startActivity(intent);
    }

    private void checkPermission(final String permission, final int permissionId, String message){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                alertDialog.setTitle("Permission required");
                alertDialog.setMessage(message);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(ContactUsActivity.this,
                                new String[]{permission}, permissionId);
                    }
                });
                alertDialog.show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{permission}, permissionId);
            }
        } else {
            // Permission has already been granted
            handleRequest(permissionId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleRequest(requestCode);
        }else{
            Toast t = Toast.makeText(this, MSG_DENIED, Toast.LENGTH_SHORT);
            t.show();
        }
    }

    private void handleRequest(int requestCode){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_PHONE:
                handleOpenCall();
                return;
            case MY_PERMISSIONS_REQUEST_SMS:
                handleOpenSMS();
                return;
        }
    }
}
