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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tourismapp.R;
import com.example.tourismapp.fragments.AttractionsFragment;
import com.example.tourismapp.fragments.HomeFragment;
import com.example.tourismapp.fragments.OlympicsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    private int currentFragmentId;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.miSearch:
                handleSearchClick();
                break;
            case R.id.miFavourite:
                handleFavouriteClick();
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (currentFragmentId){
            case R.id.b_nav_olympics:
                (menu.findItem(R.id.miSearch)).setVisible(true);
                (menu.findItem(R.id.miFavourite)).setVisible(false);
                break;
            case R.id.b_nav_attractions:
                (menu.findItem(R.id.miSearch)).setVisible(false);
                (menu.findItem(R.id.miFavourite)).setVisible(true);
                break;
            default:
                (menu.findItem(R.id.miSearch)).setVisible(false);
                (menu.findItem(R.id.miFavourite)).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
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
        switch(item.getItemId()){
            case R.id.nav_logout:
                Log.d("NAV", "logout");
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_contact_us:
                //handle contact us
                Log.d("NAV", "logout");
                startActivity(new Intent(this, ContactUsActivity.class));
                break;
            case R.id.nav_faq:
                //handle faq
                Log.d("NAV", "logout");
                startActivity(new Intent(this, FAQActivity.class));
                break;
            case R.id.nav_settings:
                //handle settings
                Log.d("NAV", "logout");
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean onBottomNavigationItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.b_nav_home:
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new HomeFragment()).commit();
                currentFragmentId = R.id.b_nav_home;
                invalidateOptionsMenu();
                break;
            case R.id.b_nav_olympics:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new OlympicsFragment()).commit();
                currentFragmentId = R.id.b_nav_olympics;
                invalidateOptionsMenu();
                break;
            case R.id.b_nav_attractions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new AttractionsFragment()).commit();
                currentFragmentId = R.id.b_nav_attractions;
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    private void handleSearchClick(){
        //show date selection dialog8
    }

    private void handleFavouriteClick(){

    }
}
