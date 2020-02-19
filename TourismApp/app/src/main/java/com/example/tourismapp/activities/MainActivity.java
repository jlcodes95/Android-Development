package com.example.tourismapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tourismapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        //navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //bottom navigation bar
        bottomNavigationView = findViewById(R.id.bnvMain);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return onBottomNavigationItemSelected(menuItem);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("NAV", "item selected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            Log.d("NAV", "logout");
//            // Handle the camera action
//            FrameLayout f = (FrameLayout)  findViewById(R.id.main_content);
//            f.removeAllViews();
            startActivity(new Intent(this, LoginActivity.class));
        }
//        if (id == R.id.nav_home) {
//            Log.d("NAV", "home");
//            // Handle the camera action
//            FrameLayout f = (FrameLayout)  findViewById(R.id.main_content);
//            f.removeAllViews();
//        } else
//        if (id == R.id.nav_add) {
//            Log.d("NAV", "ADD");
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_content, new AddFragment()).commit();
//        } else if (id == R.id.nav_list) {
//            Log.d("NAV", "LIST");
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_content, new ListFragment()).commit();
//        } else if (id == R.id.nav_update) {
//            Log.d("NAV", "UPDATE");
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_content, new UpdateFragment()).commit();
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean onBottomNavigationItemSelected(MenuItem menuItem){

        return true;
    }
}
