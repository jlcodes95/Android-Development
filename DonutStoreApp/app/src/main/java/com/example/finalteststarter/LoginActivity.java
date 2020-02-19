package com.example.finalteststarter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.finalteststarter.classes.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    final String TAG = "Donut";

    ArrayList<User> users = new ArrayList<User>();

    // setup shared preferences
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "CineplexSP";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestUserPermission();

        // create some users and add them to the app
        User u1 = new User("admin", "admin", true);
        User u3 = new User("jonsnow", "0000", false);
        User u2 = new User("peter", "1234", true);
        User u4 = new User("mary", "abcd", false);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);

        // DEBUG: print out the users to the terminal
        Log.d(TAG, "The users in the system are:");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            Log.d(TAG, i + ": " + u.getUsername());
        }
        Log.d(TAG, "-----");



        // setup sharedPreferences
        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);


        // check if there is already a username and password set
        String u = prefs.getString("username", "");
        String p = prefs.getString("password", "");


        Log.d(TAG,"SP u: "+ u);
        Log.d(TAG,"SP p: "+ p);

        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        etUsername.setText(u);
        etPassword.setText(p);

    }

    public void loginButtonPressed(View view) {

        // get username /password from UI
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        Log.d(TAG, "username; " + username);
        Log.d(TAG, "psswd; " + password);

        // check if user exists
        User u = this.getUser(username);
        if (u == null) {
            // cannot find the user
            Toast t = Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
//
//        // if user exists, check credentials
//        if (u.isAdmin() == false) {
//            // display error message
//            Toast t = Toast.makeText(getApplicationContext(), "User is not an admin!", Toast.LENGTH_SHORT);
//            t.show();
//
//        }
//
//        // at this point, the user must exist & is an admin. so check password
//        if (u.getPassword().contentEquals(password)) {
//            Intent i = new Intent(this, MainActivity.class);
//            i.putExtra("username", username);
//            i.putExtra("password", password);
//            startActivity(i);
//        }
//        // else - display error message
//        else {
//            Toast t = Toast.makeText(getApplicationContext(), "Password is wrong!!", Toast.LENGTH_SHORT);
//            t.show();
//            return;
//        }

        if (u.getPassword().contentEquals(password)) {
            if (u.isAdmin()){
                Intent i = new Intent(this, AddDonutActivity.class);
                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);
            }else{
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);
            }

        }else{
            Toast t = Toast.makeText(getApplicationContext(), "Password is wrong!!", Toast.LENGTH_SHORT);
            t.show();
            return;
        }


    }

    public User getUser(String username) {
        for (int i = 0; i < this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().contentEquals(username)) {
                return u;
            }
        }
        return null;
    }

    private void requestUserPermission(){
        requestCallPhone();
//        requestSendSMS();
    }

    private void requestCallPhone(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    private void requestSendSMS(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                            String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                requestSendSMS();
                return;
        }
    }

//
//    private void guestCustomerLoginButtonPressed(View view) {
//        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(i);
//    }
//    private void guestAdminLoginButtonPressed(View view) {
//        Intent i = new Intent(LoginActivity.this, AddDonutActivity.class);
//        startActivity(i);
//    }


}
