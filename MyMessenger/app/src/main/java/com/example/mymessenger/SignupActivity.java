package com.example.mymessenger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    public static UserList userList = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setSupportActionBar((Toolbar) findViewById(R.id.tbSignup));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickSignup(View view){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etPassword2 = findViewById(R.id.etPassword2);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        if(username.equals("")){
            etUsername.setError("Please enter a username");
        }else if (password.equals("")){
            etPassword.setError("Please enter a password");
        }else if (password2.equals("")){
            etPassword2.setError("Please confirm your password");
        }else if (!password.equals(password2)){
            etPassword.setError("Your passwords do not match");
            etPassword2.setError("Your passwords do not match");
        }else if(userList.checkIfUserExists(username)){
            etUsername.setError("Username already in use, please choose a new username");
        }else{
            // register user
            User newUser = new User(username, password);
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            userList.addUser(newUser);
            alertBox.setTitle("Success!");
            alertBox.setMessage("You've successfully registered, redirecting to login page");
            alertBox.setPositiveButton("OK.", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                   alertBoxOnClick(dialog, which);
                }
            });
            alertBox.show();
        }
    }

    private void alertBoxOnClick(DialogInterface dialog, int which){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
