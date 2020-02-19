package com.example.finalteststarter.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalteststarter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    View view;
    private static final String PHONE_NUMBER = "4165550909";

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ((Button) view.findViewById(R.id.onClickCall)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCallPhonePermission();
            }
        });

        ((Button) view.findViewById(R.id.onClickSendSMS)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSendSMSPermission();
            }
        });

        return view;
    }

    private void onClickCall(){
        String formattedPhoneNumber = "tel:" + PHONE_NUMBER;

        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse(formattedPhoneNumber));

        if (i.resolveActivity(view.getContext().getPackageManager()) != null){
            startActivity(i);
        }else{
            Log.d("onClickCall", "ERROR: cannot find app that matches ACTION_CALL intent");
        }
    }

    private void onClickSendSMS(){
        EditText etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        String number = etPhoneNumber.getText().toString();
        if (number.equals("")){
            Toast t = Toast.makeText(getContext(), "Please enter a phone number!", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        String message = "Hello, I have a question! Please call me at " + number;
        String formattedPhoneNumber = "smsto:" + PHONE_NUMBER;
        String scAddress = null;
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(formattedPhoneNumber, scAddress, message, sentIntent, deliveryIntent);
    }

    private void checkCallPhonePermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            showDialogBox("Please enable Phone permission in your device's settings");
        } else {
            // Permission has already been granted
            onClickCall();
        }
    }

    private void checkSendSMSPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            showDialogBox("Please enable SMS permission in your device's settings");
        } else {
            // Permission has already been granted
            onClickSendSMS();
        }
    }

    private void showDialogBox(String msg){
        AlertDialog.Builder dialogBox = new AlertDialog.Builder(getContext());
        dialogBox.setTitle("Access Denied");
        dialogBox.setMessage("This application does not have the necessary permission to perform this action. \n" + msg);
        dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBox.show();
    }


}
