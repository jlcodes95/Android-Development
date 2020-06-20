package com.example.labtest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_SIGNUP";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();
    }

    public void onRegisterPressed(View view) {
        Log.d(TAG, "register pressed");
        EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        EditText etLastName = (EditText) findViewById(R.id.etLastName);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (firstName.isEmpty()) {
            etFirstName.setError("Please enter first name");
        }else if (lastName.isEmpty()){
            etLastName.setError("Please enter last name");
        }else if (username.isEmpty()) {
            etUsername.setError("Please enter a username");
        }else if (password.isEmpty()) {
            etPassword.setError("Please enter a password");
        }else {
            User user = new User(firstName, lastName, username, password);
            user.setType("reg");
            addUser(user);
        }
    }

    public void onLoginPressed(View view) {
        Log.d(TAG, "login pressed");
        startActivity(new Intent(this, MainActivity.class));
    }

    private void addUser(User user) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast t = Toast.makeText(SignupActivity.this, "User created!", Toast.LENGTH_LONG);
                        t.show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
