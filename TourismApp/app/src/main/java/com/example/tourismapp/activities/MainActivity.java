package com.example.tourismapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tourismapp.R;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.TouristDatabase;
import com.example.tourismapp.fragments.AttractionsFragment;
import com.example.tourismapp.fragments.DatePickerFragment;
import com.example.tourismapp.fragments.HomeFragment;
import com.example.tourismapp.fragments.OlympicsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    private int currentFragmentId;

    public static SharedPreferences spa;
    public static final String SP_APP_NAME = "currentSessionData";
    public static TouristDatabase db;
    private static final boolean INIT_DB = false;

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

        //initialize database
        db = Room.databaseBuilder(getApplicationContext(),
                TouristDatabase.class, "usersDatabase").allowMainThreadQueries().build();
        //init initial data
        initAttractionsData();

        spa = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE);

        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.tvHeaderName))
                .setText(spa.getString("username", ""));

        int previousFragmentId = spa.getInt("previousFragment", -1);
        if (previousFragmentId != -1){
            bottomNavigationView.setSelectedItemId(previousFragmentId);
            handleFragmentChange(previousFragmentId);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.b_nav_home);
            handleFragmentChange(R.id.b_nav_home);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.miSearch:
                handleSearchClick();
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (currentFragmentId){
            case R.id.b_nav_olympics:
                (menu.findItem(R.id.miSearch)).setVisible(true);
                break;
            default:
                (menu.findItem(R.id.miSearch)).setVisible(false);
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
            spa.edit().clear().commit();
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
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean onBottomNavigationItemSelected(MenuItem menuItem){
        handleFragmentChange(menuItem.getItemId());
        return true;
    }

    private void handleFragmentChange(int id){
        switch(id){
            case R.id.b_nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new HomeFragment()).commit();
                currentFragmentId = R.id.b_nav_home;
                spa.edit().putInt("previousFragment", R.id.b_nav_home).commit();
                invalidateOptionsMenu();
                break;
            case R.id.b_nav_olympics:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new OlympicsFragment()).commit();
                currentFragmentId = R.id.b_nav_olympics;
                spa.edit().putInt("previousFragment", R.id.b_nav_olympics).commit();
                invalidateOptionsMenu();
                break;
            case R.id.b_nav_attractions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new AttractionsFragment()).commit();
                currentFragmentId = R.id.b_nav_attractions;
                spa.edit().putInt("previousFragment", R.id.b_nav_attractions).commit();
                invalidateOptionsMenu();
                break;
        }
    }

    private void handleSearchClick(){
        //show date selection dialog
        Log.d("DATE_PICKER", "working");
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDatePickerResultSet(int year, int month, int day){
        Log.d("DATE_PICKER", String.format("The date is %02d/%02d/%02d", year, month, day));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new OlympicsFragment(String.format("%02d%02d%02d", year, month, day))).commit();
        currentFragmentId = R.id.b_nav_olympics;
        invalidateOptionsMenu();
    }

    /**
     * used to initialize attractions for testing
     */
    private void initAttractionsData(){
        //attraction empty
        if (db.attractionDAO().getAttractionList().size() == 0){
            db.attractionDAO().deleteAttractions();
            Log.d("INIT_DB", "initializing");
            Attraction a1 = new Attraction();
            a1.setName("Tokyo Tower");
            a1.setAddress("4 Chome-2-8 Shibakoen, Minato City, Tokyo 105-0011, Japan");
            a1.setDescription("Tokyo Tower is a communications and observation tower in the Shiba-koen district of Minato, Tokyo, Japan. At 332.9 meters, it is the second-tallest structure in Japan. The structure is an Eiffel Tower-inspired lattice tower that is painted white and international orange to comply with air safety regulations.");
            a1.setImagePath("img_tokyo_tower");
            db.attractionDAO().addAttraction(a1);
            Attraction a2 = new Attraction();
            a2.setName("Sky Tree");
            a2.setAddress("1 Chome-1-2 Oshiage, Sumida City, Tokyo 131-0045, Japan");
            a2.setDescription("Tokyo Skytree is a broadcasting and observation tower in Sumida, Tokyo. It became the tallest structure in Japan in 2010 and reached its full height of 634.0 metres in March 2011, making it the tallest tower in the world, displacing the Canton Tower,[4][5] and the second tallest structure in the world after the Burj Khalifa (829.8 m/2,722 ft).");
            a2.setImagePath("img_sky_tree");
            db.attractionDAO().addAttraction(a2);
            Attraction a3 = new Attraction();
            a3.setName("Akihabara");
            a3.setAddress("Akihabara, Tokyo, Japan");
            a3.setDescription("Akihabara is a buzzing shopping hub famed for its electronics retailers, ranging from tiny stalls to vast department stores like Yodobashi Multimedia Akiba. Venues specializing in manga, anime, and video games include Tokyo Anime Center, for exhibits and souvenirs, and Radio Kaikan with 10 floors of toys, trading cards, and collectibles. Staff dressed as maids or butlers serve tea and desserts at nearby maid cafes.");
            a3.setImagePath("img_akihabara");
            db.attractionDAO().addAttraction(a3);
            Attraction a4 = new Attraction();
            a4.setName("Life Sized Gundam Statue");
            a4.setAddress("1, Aomi, Koto City, Tokyo 135-0064, Japan");
            a4.setDescription("Giant white statue of cult sci-fi novel & anime character, with nighttime music & lights.");
            a4.setImagePath("img_unicorn_gundam_statue");
            db.attractionDAO().addAttraction(a4);
            Attraction a5 = new Attraction();
            a5.setName("Sensō-ji");
            a5.setAddress("2 Chome-3-1 Asakusa, Taito City, Tokyo 111-0032, Japan");
            a5.setDescription("Sensō-ji is an ancient Buddhist temple located in Asakusa, Tokyo, Japan. It is Tokyo's oldest temple, and one of its most significant. Formerly associated with the Tendai sect of Buddhism, it became independent after World War II.");
            a5.setImagePath("img_senso_ji");
            db.attractionDAO().addAttraction(a5);
            Attraction a6 = new Attraction();
            a6.setName("Meiji Jingu");
            a6.setAddress("1-1 Yoyogikamizonocho, Shibuya City, Tokyo 151-8557, Japan");
            a6.setDescription("Meiji Shrine, located in Shibuya, Tokyo, is the Shinto shrine that is dedicated to the deified spirits of Emperor Meiji and his wife, Empress Shōken. The shrine does not contain the emperor's grave, which is located at Fushimi-momoyama, south of Kyoto.");
            a6.setImagePath("img_meiji_jingu");
            db.attractionDAO().addAttraction(a6);
            Attraction a7 = new Attraction();
            a7.setName("Ueno Onshi Park");
            a7.setAddress("Uenokoen, Taito City, Tokyo 110-0007, Japan");
            a7.setDescription("Ueno Park is a spacious public park in the Ueno district of Taitō, Tokyo, Japan. The park was established in 1873 on lands formerly belonging to the temple of Kan'ei-ji.");
            a7.setImagePath("img_ueno_onshi_park");
            db.attractionDAO().addAttraction(a7);
        }
        Log.d("INIT_DB", ""+db.attractionDAO().getAttractionList().size());
    }
}
