package com.example.mealdelivery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mealdelivery.DataTransferObjects.UserRoleDto;
import com.example.mealdelivery.Fragments.MySubscriptionsFragment;
import com.example.mealdelivery.Fragments.PickupFragment;
import com.example.mealdelivery.Fragments.SubscribeFragment;
import com.example.mealdelivery.Fragments.SubscriptionsFragment;
import com.example.mealdelivery.R;
import com.example.mealdelivery.Roles;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_DASHBOARD";
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fContainer;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar tb = findViewById(R.id.tbDashboard);
        setSupportActionBar(tb);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fContainer = (FrameLayout) findViewById(R.id.fContainer);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_subscriptions:
                        Log.d(TAG, "subscription tab clicked");
                        onBottomNavItemSelected(R.id.item_subscriptions);
                        break;
                    case R.id.item_pickup:
                        Log.d(TAG, "pickup tab clicked");
                        onBottomNavItemSelected(R.id.item_pickup);
                        break;
                    case R.id.item_subscribe:
                        Log.d(TAG, "subscribe tab clicked");
                        onBottomNavItemSelected(R.id.item_subscribe);
                        break;
                    case R.id.item_my_subscriptions:
                        Log.d(TAG, "my subscription tab clicked");
                        onBottomNavItemSelected(R.id.item_my_subscriptions);
                        break;
                }
                return true;
            }
        });

        UserRoleDto userRoleDto = (UserRoleDto) getIntent().getSerializableExtra("userRoleDto");
        if (userRoleDto != null) {
            Log.d(TAG, ""+(userRoleDto.getRole() == Roles.ADMIN));
            if (userRoleDto.getRole() == Roles.ADMIN) {
                Log.d(TAG, "admin layout");
                bottomNavigationView.inflateMenu(R.menu.menu_nav_bottom_admin);
                onBottomNavItemSelected(R.id.item_subscriptions);
                return;
            }
        }
        Log.d(TAG, "customer layout");
        bottomNavigationView.inflateMenu(R.menu.menu_nav_bottom_customer);
        onBottomNavItemSelected(R.id.item_subscribe);
    }

    /**
     * details page menu creation
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    /**
     * details page menu item selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_signout:
                onSignOutPressed();
        }
        return true;
    }

    public void onSignOutPressed() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
//                        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void onBottomNavItemSelected(int id) {
        switch (id) {
            case R.id.item_subscriptions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fContainer, new SubscriptionsFragment()).commit();
                break;
            case R.id.item_pickup:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fContainer, new PickupFragment()).commit();
                break;
            case R.id.item_subscribe:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fContainer, new SubscribeFragment()).commit();
                break;
            case R.id.item_my_subscriptions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fContainer, new MySubscriptionsFragment()).commit();
                break;
        }
    }

}
