/* -----------------------------------
 * John Lin
 * 101296282
 * ----------------------------------- */
package com.example.rydeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static ArrayList<User> USERS;
    private static final String MSG_INCORRECT_CREDENTIALS = "Your credentials are incorrect, please try again.";
    private static final String MSG_MISSING_INPUT = "Some fields are missing, please fill in the marked fields.";
    private static final String MSG_MISSING_USERNAME = "Please type your username.";
    private static final String MSG_MISSING_PASSWORD = "Please type your password.";
    private static final String MSG_MISSING_PHONENUMBER= "Please type your phone number.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declaring user
        if (USERS == null){
            USERS = new ArrayList<User>();
            User userPeter = new User("peter", "0000", "4165551111");
            USERS.add(userPeter);
        }
        Log.d("TESTING", ""+USERS.size());
    }

    public void onClickAttemptLogin(View view){
        try{
            EditText etUsername = (EditText) findViewById(R.id.etUsername);
            EditText etPassword = (EditText) findViewById(R.id.etPassword);
            EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();

            //check for valid input
            if (username.equals("")){
                etUsername.setError(MSG_MISSING_USERNAME);
                throw new MissingInputException(MSG_MISSING_INPUT);
            }else if(password.equals("")){
                etPassword.setError(MSG_MISSING_PASSWORD);
                throw new MissingInputException(MSG_MISSING_INPUT);
            }else if(phoneNumber.equals("")){
                etPhoneNumber.setError(MSG_MISSING_PHONENUMBER);
                throw new MissingInputException(MSG_MISSING_INPUT);
            }

            //attempt to login
            User currentUser = attemptSignin(username, password, phoneNumber);
            if (currentUser == null){
                //sign up
                currentUser = handleSignup(username, password, phoneNumber);
            }
            USERS.add(currentUser);

            //set intent and send bundle
            Intent mainIntent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", currentUser.getUsername());
//            bundle.putString("password", currentUser.getPassword());
            bundle.putString("phoneNumber", currentUser.getPhoneNumber());
            mainIntent.putExtras(bundle);

            startActivity(mainIntent);

        }catch(Exception e){
            resetPasswordAndPhoneFields();
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }

    }

    private void resetPasswordAndPhoneFields(){
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword.setText("");
        etPhoneNumber.setText("");
    }

    private User attemptSignin(String username, String password, String phoneNumber) throws CredentialException{
        for(int i = 0; i < USERS.size(); i++){
            if (USERS.get(i).getUsername().equals(username)){
                if (USERS.get(i).isMatchingCredentials(username, password, phoneNumber)){
                    return USERS.get(i);
                }else{
                    throw new CredentialException(MSG_INCORRECT_CREDENTIALS);
                }
            }
        }
        return null;
    }

    private User handleSignup(String username, String password, String phoneNumber){
        User newUser = new User(username, password, phoneNumber);
        return newUser;
    }
}
