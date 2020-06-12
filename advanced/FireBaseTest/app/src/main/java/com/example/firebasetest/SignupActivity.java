package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private final String TAG = "DBUG_SIGNUP";
    private FirebaseAuth auth;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
    }

    public void onLoginRedirect(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onSignupPressed(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        if (!email.isEmpty() && !password.isEmpty() && password.contentEquals(password2)) {
            Log.d(TAG, "Attempting to create account...");
            createAccount(email, password);
        }
    }

    public void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            redirect();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void redirect() {
        Intent redirectIntent = new Intent(this, MainActivity.class);
        startActivity(redirectIntent);
    }
}
