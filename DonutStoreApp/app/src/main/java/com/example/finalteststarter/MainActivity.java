package com.example.finalteststarter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


// 1. Import the correct Toolbar package
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalteststarter.fragments.ContactUsFragment;
import com.example.finalteststarter.fragments.FAQFragment;
import com.example.finalteststarter.fragments.HomeScreenFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    final String TAG = "DONUT";

    // UI variables
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbarDonutShop);
        setSupportActionBar(toolbar);

        // Setup the navigation drawer
        drawer = findViewById(R.id.mainActivityDrawerLayout);
        navigationView = findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //@TODO: Write code to dynamically load the home screen fragment into the <FrameLayout>
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayoutMain, new HomeScreenFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.miLogout:
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.mainActivityDrawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // home screen
        if (id == R.id.nav_home) {
            // @TODO: Reload the home screen fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutMain, new HomeScreenFragment()).commit();
//            Toast t = Toast.makeText(getApplicationContext(), "SHOW HOME SCREEN", Toast.LENGTH_SHORT);
//            t.show();

        }
        // product listing screen
        else if (id == R.id.nav_donut_list) {
            //@TODO: Write code to go to the product listing page
            startActivity(new Intent(this, ProductListActivity.class));
//            Toast t = Toast.makeText(getApplicationContext(), "Show all products!", Toast.LENGTH_SHORT);
//            t.show();
        }
        else if (id == R.id.nav_faq) {
            //@TODO: Write the code to go to the FAQ fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutMain, new FAQFragment()).commit();
//            Toast t = Toast.makeText(getApplicationContext(), "FAQ!", Toast.LENGTH_SHORT);
//            t.show();
        }
        // buy donut screen
        else if (id == R.id.nav_contact_us) {
            //@TODO: Write the code to load the Contact Us fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutMain, new ContactUsFragment()).commit();
//            Toast t = Toast.makeText(getApplicationContext(), "Contact Us!", Toast.LENGTH_SHORT);
//            t.show();
        }

        // after selecting a drawer menu item, show new screen and close the drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
