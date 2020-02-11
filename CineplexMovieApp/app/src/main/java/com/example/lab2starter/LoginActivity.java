package com.example.lab2starter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    final String TAG = "CINEPLEX";

    // data source
    ArrayList<User> users = new ArrayList<User>();

    // Shared Preferences
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "CineplexSP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // -------------------------------------
        // Initialize Shared Preferences
        // --------------------------------------

        // @TODO: Write code to initialize shared preferences
        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        String username = prefs.getString("username", "");
        if (!username.equals("")){
            Log.d("SAVED_LOGIN", username);
            ((EditText) findViewById(R.id.etUsername)).setText(username);
            ((EditText) findViewById(R.id.etPassword)).setText(prefs.getString("password", ""));
        }

        
        // --------------------------------------
        // 1. create some users and add them to the app
        // --------------------------------------
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
    }

    public void loginButtonPressed(View view) {

        // get username /password from UI
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User u = this.getUser(username);

        // --------------------------------------
        // 1. If you cannot find the user in the system, then show an error message and exit
        // --------------------------------------
        if (u == null) {
            // cannot find the user
            Toast t = Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        else {
            // do nothing, user exists!
        }


        // --------------------------------------
        // 2. At this point, we know the user exists, so continue.....
        // --------------------------------------
        // 2a. Check if password is correct
        if (u.getPassword().contentEquals(password)) {
            // If password correct, go to next screen (only if administrator)

            if (u.isAdmin()){
                Intent i = new Intent(this, MovieListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", u.getUsername());
                bundle.putString("password", u.getPassword());
                i.putExtras(bundle);
                startActivity(i);
            }else{
                Log.d("LOGIN_ERR", "loginerror");
                AlertDialog.Builder popupBox = new AlertDialog.Builder(this);
                popupBox.setTitle("Login error!");
                popupBox.setMessage("Sorry, only administrators can login.");
                popupBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("LOGIN_ERR_DIALOG", "clicked ok.");
                    }
                });
                popupBox.show();
            }

        }
        else {
            // else - display error message and quit
            Toast t = Toast.makeText(getApplicationContext(), "Password is wrong!!", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
    }

    // -----------------------
    // HELPER FUNCTIONS
    // -----------------------

    // Searches for a user with a specific username.
    // If user found, return a reference to that User object
    // If not found, return null
    public User getUser(String username) {
        for (int i = 0; i < this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().contentEquals(username)) {
                return u;
            }
        }
        return null;
    }



    // This function is here to let you easily transition from Login screen to next screen.
    // It's here for debugging purposes.

    // You should remove this function & button before submitting your final code.
    public void skipLoginButtonPressed(View view) {
        Intent i = new Intent(this, MovieListActivity.class);
        startActivity(i);
    }

}
