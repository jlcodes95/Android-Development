package com.example.mealdelivery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mealdelivery.Data.UserRole;
import com.example.mealdelivery.R;
import com.example.mealdelivery.Roles;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    private final String ERR_DB_READ = "ERR: Cannot read data; Please contact support.";
    private static final int RC_SIGN_IN = 14;
    private static final int RC_DASHBOARD = 11;
    private FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setLoginUI();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        redirectLogin(currentUser);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                redirectLogin(user);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }else if (requestCode == RC_DASHBOARD) {
            if (resultCode == RESULT_OK) {
                setLoginUI();
            }
        }
    }

    private void redirectLogin(FirebaseUser user) {
        if (user != null) {
            getUserData(user);
        }
    }

    private void getUserData(final FirebaseUser user) {
        db.collection("userRoles")
                .whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                UserRole userRole = document.toObject(UserRole.class);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (userRole != null) {
                                    goToDashBoardWithUserRole(userRole);
                                }else {
                                    Toast.makeText(MainActivity.this, ERR_DB_READ, Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Log.d(TAG, "inserting");
                                //insert user data into db
                                insertUserDataAsCustomer(user);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void insertUserDataAsCustomer(FirebaseUser user) {
        // Create a new user with a first and last name
        final UserRole userRole = new UserRole(user.getUid(), Roles.CUSTOMER);

        // Add a new document with a generated ID
        db.collection("userRoles")
                .add(userRole)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        //redirect customer page
                        goToDashBoardWithUserRole(userRole);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void goToDashBoardWithUserRole(UserRole userRole) {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        intent.putExtra("userRole", userRole);
        startActivityForResult(intent, RC_DASHBOARD);
    }

    private void setLoginUI() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
}
