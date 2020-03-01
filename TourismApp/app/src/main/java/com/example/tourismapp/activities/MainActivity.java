package com.example.tourismapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.components.NotificationPublisher;
import com.example.tourismapp.components.TouristDatabase;
import com.example.tourismapp.fragments.AttractionsFragment;
import com.example.tourismapp.fragments.DatePickerFragment;
import com.example.tourismapp.fragments.HomeFragment;
import com.example.tourismapp.fragments.OlympicsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    private int currentFragmentId;
    private boolean showBell;
    private Date selectedDate;

    public static TouristDatabase db;
    public static SharedPreferences spa;
    public static final String SP_APP_NAME = "currentSessionData";
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String MSG_NOTIFICATION = "There's an olympic game hosted today.";
    private final String CHANNEL_ID = "TOURISM_APP_CHANNEL_ID";

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

        spa = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE);

        String username = spa.getString("username", "");
        if (username.equals("")){
         startActivity(new Intent(this, LoginActivity.class));
         return;
        }
        Log.d(TAG, "username: " + username);

        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.tvHeaderName))
                .setText(username);

        int previousFragmentId = spa.getInt("previousFragment", -1);
        if (previousFragmentId != -1){
            bottomNavigationView.setSelectedItemId(previousFragmentId);
            handleFragmentChange(previousFragmentId);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.b_nav_home);
            handleFragmentChange(R.id.b_nav_home);
        }

        //notification channel init
        createNotificationChannel();

    }

    /**
     * main menu creation
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * main menu options selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.miSearch:
                handleSearchClick();
                break;
            case R.id.miNotification:
                handleNotificationClick();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (currentFragmentId){
            case R.id.b_nav_olympics:
                (menu.findItem(R.id.miSearch)).setVisible(true);
                if (this.showBell) (menu.findItem(R.id.miNotification)).setVisible(true);
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

    /**
     * handle bottom navigation menu item selection
     * @param menuItem
     * @return
     */
    private boolean onBottomNavigationItemSelected(MenuItem menuItem){
        handleFragmentChange(menuItem.getItemId());
        return true;
    }

    /**
     * when fragment changes
     * @param id - for the bottom navigation option
     */
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
                goToAttractions();
                break;
        }
    }

    /**
     * handle search click
     */
    private void handleSearchClick(){
        //show date selection dialog
        Log.d("DATE_PICKER", "working");
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * handle notification click
     */
    private void handleNotificationClick(){
        Date now = new Date();
        Log.d(TAG, ""+(this.selectedDate.getTime() - now.getTime()));
        scheduleNotification(getNotification(MSG_NOTIFICATION), 5000);
        Toast t = Toast.makeText(this, "You have scheduled a notification on: " + this.selectedDate.toString(), Toast.LENGTH_SHORT);
        t.show();
    }

    /**
     * handles scheduling of notification using alarm manager
     * referenced from https://gist.github.com/BrandonSmith/6679223
     * @param notification the notification to be shown
     * @param delay delay in ms
     */
    private void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    /**
     * Referenced from https://gist.github.com/BrandonSmith/6679223
     * @param message text to show in notification
     * @return
     */
    private Notification getNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Scheduled Notification")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    /**
     * creating notification channel
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TEST_NOTIFICATION";
            String description = "A TESTING NOTIFICATION DESC";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * handle date picker results from olympics fragment date picker
     * @param year
     * @param month
     * @param day
     */
    public void onDatePickerResultSet(int year, int month, int day){
        Log.d("DATE_PICKER", String.format("The date is %02d/%02d/%02d", year, month, day));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new OlympicsFragment(String.format("%02d%02d%02d", year, month, day))).commit();
        currentFragmentId = R.id.b_nav_olympics;
        this.showBell = true;
        try{
            this.selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(String.format("%d/%d/%d", day, month, year));
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }

        invalidateOptionsMenu();
    }

    /**
     * redirect to attractions fragment
     */
    public void goToAttractions(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new AttractionsFragment()).commit();
        currentFragmentId = R.id.b_nav_attractions;
        spa.edit().putInt("previousFragment", R.id.b_nav_attractions).commit();
        invalidateOptionsMenu();
    }
}
