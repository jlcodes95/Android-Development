package com.example.tourismapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.components.TouristDatabase;
import com.example.tourismapp.components.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sp;
    public static final String SP_NAME = "savedUserCredentials";
    public static TouristDatabase db;
    private static final String ERR_MSG_EMPTY_USERNAME = "Username cannot be empty";
    private static final String ERR_MSG_EMPTY_PASSWORD = "Password cannot be empty";
    private static final String ERR_MSG_USER_NOT_FOUND = "User is not registered, would you like to sign up?";
    private static final String ERR_MSG_PASSWORD_INCORRECT = "Password incorrect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize database
        db = Room.databaseBuilder(getApplicationContext(),
                TouristDatabase.class, "usersDatabase").allowMainThreadQueries().build();

        //initialize sharedPreference
        sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (!username.equals("") && !password.equals("")){
            ((EditText) findViewById(R.id.etUsername)).setText(username);
            ((EditText) findViewById(R.id.etPassword)).setText(password);
            ((CheckBox) findViewById(R.id.chkRemember)).setChecked(true);
        }

    }

    public void onClickLogin(View view){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        etUsername.setError(null);
        etPassword.setError(null);

        try{
            final String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            //fields are not filled
            if (username.equals("")){
                etUsername.setError(ERR_MSG_EMPTY_USERNAME);
                throw new Exception(ERR_MSG_EMPTY_USERNAME);
            }else if (password.equals("")){
                etPassword.setError(ERR_MSG_EMPTY_PASSWORD);
                throw new Exception(ERR_MSG_EMPTY_PASSWORD);
            }

            List<User> users = db.userDAO().getUserByUsername(username);
            if (users.size() == 0){
                //show dialog to redirect sign up page
                showLoginAlertBox(username);
            }
            //username & password dont match
            User user = users.get(0);
            if (!user.getPassword().equals(password)){
                etPassword.setError(ERR_MSG_PASSWORD_INCORRECT);
                throw new Exception(ERR_MSG_PASSWORD_INCORRECT);
            }

            //otherwise, save data (if checked) & redirect home
            CheckBox checkbox = findViewById(R.id.chkRemember);
            setSharedPreferences(checkbox.isChecked(), username, password);

            //TODO: add code for redirect
            startActivity(new Intent(this, MainActivity.class));
//            Toast t = Toast.makeText(this, "CORRECT USER CREDENTIALS", Toast.LENGTH_SHORT);
//            t.show();

        }catch(Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }

    private void setSharedPreferences(boolean isChecked, String username, String password){
        SharedPreferences.Editor spEditor = sp.edit();
        if (isChecked){
            //TODO: add code for saving local data
            spEditor.putString("username", username);
            spEditor.putString("password", password);
            spEditor.apply();
        }else{
            spEditor.clear();
            spEditor.apply();
        }
    }

    private void showLoginAlertBox(final String username){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setTitle("Error");
        alertBox.setMessage(ERR_MSG_USER_NOT_FOUND);
        alertBox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        alertBox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBox.show();
    }

    public void onClickRedirectSignup(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }
}
