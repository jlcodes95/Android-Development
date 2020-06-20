package com.example.permissionsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TAG ="TEST";

    static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // CLICK HANDLERS
    public void sendSMSPressed(View view) {
        Log.d(TAG, "Send sms pressed");
        EditText text = (EditText) findViewById(R.id.etPhoneNumber);
        this.sendSMS(text.getText().toString(), "This is a test message");
    }

    public void callPhonePressed(View view) {
        Log.d(TAG, "Call phone pressed");
        EditText text = (EditText) findViewById(R.id.etPhoneNumber);

        //ask for permission to make phone call
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {
            // @TODO: Permission was granted, so do something now
            this.callPhone(text.getText().toString());
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
            // @TODO: Update your UI to explain to user why you need permission.
            // @TODO: Provide the user with a "cancel" or "no thanks" option
            // @TODO: Also ensure your app can continue functioning even if the user denies permission

            //show rationale before request
            AlertDialog.Builder popup = new AlertDialog.Builder(this);
            popup.setTitle("Permission Request")
                    .setMessage("We need permission to call someone")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    CALL_PHONE_PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("NO", null);
            popup.show();


        } else {
            // Show the default Android permissions popup box
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EditText text = (EditText) findViewById(R.id.etPhoneNumber);
                callPhone(text.getText().toString());
            }else {
                //execute backup plan
                //OPT 1: disable button
                //OPT 2: Tell user why need permission if show reason => do something else do something
                //OPT 3: keep showing allow/deny popup until they say yes

                boolean showReason = shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE);
                if (showReason) {
                    Toast.makeText(this, "GIVE RATIONALE HERE", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "CANNOT MAKE CALLS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // HELPER FUNCTIONS
    private void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void callPhone(String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNo));
        EditText text = (EditText) findViewById(R.id.etPhoneNumber);
        text.setText("");
        this.startActivity(intent);
    }

}
