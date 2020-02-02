package com.example.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static User[] USERS = new User[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //store predefined users into users array
        USERS[0] = new User("gb1001", "pwd1001", "George", "Brown");
        USERS[1] = new User("tor1010", "pwd1010", "Tor", "Onto");
        USERS[2] = new User("wloo99", "pwd99", "Water", "Loo");
    }

    public void onClickLogin(View view){
        Log.d("TESTING", "BUTTON CLICKED");
        try{
            String username = getUsername();
            String password = getPassword();

            User verifiedUser = null;
            //parse users db
            for (int i = 0; i < USERS.length; i++){
                if (USERS[i].getUsername().equals(username) && USERS[i].getPassword().equals(password)){
                    verifiedUser = USERS[i];
                    break;
                }
            }
            if (verifiedUser == null){
                throw new Exception("User not found!");
            }

            //create intent and send bundle
            Intent mainIntent = new Intent(this, MainActivity.class);
            Bundle mainBundle = new Bundle();

            mainBundle.putString("firstName", verifiedUser.getFirstName());
            mainBundle.putString("lastName", verifiedUser.getLastName());

            mainIntent.putExtras(mainBundle);
            startActivity(mainIntent);

        }catch(Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }

    private String getUsername() throws Exception{
        String result = getEditTextString(R.id.etUsername);
        if (result.equals("")){
            throw new Exception("Please enter username!");
        }
        return result;
    }

    private String getPassword() throws Exception{
        String result = getEditTextString(R.id.etPassword);
        if (result.equals("")){
            throw new Exception("Please enter password!");
        }
        return result;
    }

    private String getEditTextString(int id){
        EditText et = (EditText) findViewById(id);
        return et.getText().toString();
    }

}
