package com.example.tourismapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.components.User;

import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private static final String ERR_MSG_EMPTY_USERNAME = "Username cannot be empty";
    private static final String ERR_MSG_EMPTY_PASSWORD = "Password cannot be empty";
    private static final String ERR_MSG_EMPTY_PASSWORD2 = "Password Confirmation cannot be empty";
    private static final String ERR_MSG_USER_ALREADY_REGISTERED = "User is already registered";
    private static final String ERR_MSG_PASSWORD_MISMATCH = "Passwords do not match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.tbSignup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if username is carried over from signin page
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                ((EditText) findViewById(R.id.etUsername)).setText(bundle.getString("username"));
            }
        }
    }

    public void onClickSignup(View view){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etPassword2 = findViewById(R.id.etPassword2);
        etUsername.setError(null);
        etPassword.setError(null);
        etPassword2.setError(null);

        try{
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String password2 = etPassword2.getText().toString();

            //check validity of each field
            if (username.equals("")){
                etUsername.setError(ERR_MSG_EMPTY_USERNAME);
                throw new Exception(ERR_MSG_EMPTY_USERNAME);
            }else if (password.equals("")){
                etPassword.setError(ERR_MSG_EMPTY_PASSWORD);
                throw new Exception(ERR_MSG_EMPTY_PASSWORD);
            }else if (password2.equals("")){
                etPassword2.setError(ERR_MSG_EMPTY_PASSWORD2);
                throw new Exception(ERR_MSG_EMPTY_PASSWORD2);
            }else if (!password.equals(password2)){
                etPassword2.setError(ERR_MSG_PASSWORD_MISMATCH);
                throw new Exception(ERR_MSG_PASSWORD_MISMATCH);
            }else {
                //check if user already exists in db
                List<User> users = LoginActivity.db.userDAO().getUserByUsername(username);
                if (users.size() != 0) {
                    etUsername.setError(ERR_MSG_USER_ALREADY_REGISTERED);
                    throw new Exception(ERR_MSG_USER_ALREADY_REGISTERED);
                }

                //otherwise sign up and redirect to login
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                LoginActivity.db.userDAO().addUser(user);
                startActivity(new Intent(this, LoginActivity.class));
            }


        }catch(Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
