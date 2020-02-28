package com.example.mymessenger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;
    public static final String SP_NAME = "SAVED_USER";
    public static UserList userList = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String savedUsername = sp.getString("username", "");
        if (!savedUsername.equals("")){
            ((EditText) findViewById(R.id.etUsername)).setText(savedUsername);
            ((EditText) findViewById(R.id.etPassword)).setText(sp.getString("password", ""));
            ((CheckBox) findViewById(R.id.cbRemember)).setChecked(true);
        }
    }

    public void onClickLogin(View view){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        CheckBox cbRemember = findViewById(R.id.cbRemember);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("")){
            etUsername.setError("Please enter a username");
        }else if (password.equals("")) {
            etPassword.setError("Please enter a password");
        }

        try{
            User user = userList.validateUser(username, password);
            if (user == null){
                throw new Exception("User doesn't exist!");
            }


            if(cbRemember.isChecked()){
                SharedPreferences.Editor prefsEditor = sp.edit();
                prefsEditor.putString("username", username);
                prefsEditor.putString("password", password);
                prefsEditor.apply();
            }else{
                SharedPreferences.Editor prefsEditor = sp.edit();
                prefsEditor.clear();
                prefsEditor.apply();
            }

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);


        }catch (Exception e){
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            alertBox.setTitle("Error!");
            alertBox.setMessage(e.getMessage());
            alertBox.setPositiveButton("OK.", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    alertBoxOnClick(dialog, which);
                }
            });
            alertBox.show();
        }
    }

    public void onClickRedirectSignup(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }

    private void alertBoxOnClick(DialogInterface dialog, int which){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
