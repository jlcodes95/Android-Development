package com.example.labtest2;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
    }

    public void onLoginPressed(View view) {
        Log.d(TAG, "login pressed");
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty()) {
            etUsername.setError("Please enter a username");
        }else if (password.isEmpty()) {
            etPassword.setError("Please enter a password");
        }else {
            findUserByUsernameAndPassword(username, password);
        }

    }

    public void onRegisterPressed(View view) {
        Log.d(TAG, "register pressed");
        startActivity(new Intent(this, SignupActivity.class));
    }

    private void findUserByUsernameAndPassword(String username, String password) {
        Log.d(TAG, "attempting to find user / password");
        db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                User user = document.toObject(User.class);
                                Log.d(TAG, "username: " + user.getUsername() + "; " + user.getType());
                                if (user.getType().contentEquals("reg")) {
                                    startActivity(new Intent(MainActivity.this, CaseListActivity.class));
                                }else {
                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);
                                }
                            }else {
                                Log.d(TAG, "No Results");
                                Toast.makeText(MainActivity.this, "Username and password do not match any of our records.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
