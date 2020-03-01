package com.example.tourismapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.TouristDatabase;
import com.example.tourismapp.components.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences spa;
    private static final String SP_NAME = "savedUserCredentials";
    private static final String SP_APP_NAME = "currentSessionData";
    public static TouristDatabase db;
    private static final String ERR_MSG_EMPTY_USERNAME = "Username cannot be empty";
    private static final String ERR_MSG_EMPTY_PASSWORD = "Password cannot be empty";
    private static final String ERR_MSG_USER_NOT_FOUND = "User is not registered, would you like to sign up?";
    private static final String ERR_MSG_PASSWORD_INCORRECT = "Password incorrect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize database
        db = Room.databaseBuilder(getApplicationContext(),
                TouristDatabase.class, "usersDatabase").allowMainThreadQueries().build();

        //initialize sharedPreference
        sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        //spa for use within app only, if on login screen, clear
        spa = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE);
        spa.edit().clear().commit();

        //initialize data if not exist
        initData();

        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (!username.equals("") && !password.equals("")){
            ((EditText) findViewById(R.id.etUsername)).setText(username);
            ((EditText) findViewById(R.id.etPassword)).setText(password);
            ((CheckBox) findViewById(R.id.chkRemember)).setChecked(true);
        }

    }

    /**
     * attempt user login
     * @param view
     */
    public void onClickLogin(View view){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        etUsername.setError(null);
        etPassword.setError(null);

        try{
            final String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            //fields are not filled
            if (username.equals("")){
                etUsername.setError(ERR_MSG_EMPTY_USERNAME);
                throw new Exception(ERR_MSG_EMPTY_USERNAME);
            }else if (password.equals("")){
                etPassword.setError(ERR_MSG_EMPTY_PASSWORD);
                throw new Exception(ERR_MSG_EMPTY_PASSWORD);
            }

            List<User> users = db.userDAO().getUserByUsername(username);
            if (users.size() == 0){
                //show dialog to redirect sign up page
                showLoginAlertBox(username);
            }
            //username & password dont match
            User user = users.get(0);
            if (!user.getPassword().equals(password)){
                etPassword.setError(ERR_MSG_PASSWORD_INCORRECT);
                throw new Exception(ERR_MSG_PASSWORD_INCORRECT);
            }

            //otherwise, save data (if checked) & redirect home
            CheckBox checkbox = findViewById(R.id.chkRemember);
            setSharedPreferences(checkbox.isChecked(), username, password);

            //TODO: add code for redirect
            spa.edit().putString("username", user.getUsername()).commit();
            if (user.isAdmin()){
                startActivity(new Intent(this, AddAttractionActivity.class));
            }else{
                startActivity(new Intent(this, MainActivity.class));
            }

        }catch(Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }

    /**
     * saves shared preference for "remember me"
     * @param isChecked
     * @param username
     * @param password
     */
    private void setSharedPreferences(boolean isChecked, String username, String password){
        SharedPreferences.Editor spEditor = sp.edit();
        if (isChecked){
            //TODO: add code for saving local data
            spEditor.putString("username", username);
            spEditor.putString("password", password);
            spEditor.apply();
        }else{
            spEditor.clear();
            spEditor.apply();
        }
    }

    /**
     * login error message handling
     * @param username
     */
    private void showLoginAlertBox(final String username){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setTitle("Error");
        alertBox.setMessage(ERR_MSG_USER_NOT_FOUND);
        alertBox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        alertBox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBox.show();
    }

    /**
     * go to sign up
     * @param view
     */
    public void onClickRedirectSignup(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }

    /**
     * used to initialize attractions for testing
     */
    private void initData(){

        //admin user
        if (this.db.userDAO().getUserByUsername("admin").size() == 0){
            this.db.userDAO().deleteUsers();
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setAdmin(true);
            this.db.userDAO().addUser(user);
        }

        //attraction empty
        if (this.db.attractionDAO().getAttractionList().size() == 0){
            this.db.attractionDAO().deleteAttractions();
            Log.d("INIT_DB", "initializing");
            Attraction a1 = new Attraction();
            a1.setName("Tokyo Tower");
            a1.setAddress("4 Chome-2-8 Shibakoen, Minato City, Tokyo 105-0011, Japan");
            a1.setDescription("Tokyo Tower is a communications and observation tower in the Shiba-koen district of Minato, Tokyo, Japan. At 332.9 meters, it is the second-tallest structure in Japan. The structure is an Eiffel Tower-inspired lattice tower that is painted white and international orange to comply with air safety regulations.");
            a1.setImagePath("img_tokyo_tower");
            this.db.attractionDAO().addAttraction(a1);
            Attraction a2 = new Attraction();
            a2.setName("Sky Tree");
            a2.setAddress("1 Chome-1-2 Oshiage, Sumida City, Tokyo 131-0045, Japan");
            a2.setDescription("Tokyo Skytree is a broadcasting and observation tower in Sumida, Tokyo. It became the tallest structure in Japan in 2010 and reached its full height of 634.0 metres in March 2011, making it the tallest tower in the world, displacing the Canton Tower,[4][5] and the second tallest structure in the world after the Burj Khalifa (829.8 m/2,722 ft).");
            a2.setImagePath("img_sky_tree");
            this.db.attractionDAO().addAttraction(a2);
            Attraction a3 = new Attraction();
            a3.setName("Akihabara");
            a3.setAddress("Akihabara, Tokyo, Japan");
            a3.setDescription("Akihabara is a buzzing shopping hub famed for its electronics retailers, ranging from tiny stalls to vast department stores like Yodobashi Multimedia Akiba. Venues specializing in manga, anime, and video games include Tokyo Anime Center, for exhibits and souvenirs, and Radio Kaikan with 10 floors of toys, trading cards, and collectibles. Staff dressed as maids or butlers serve tea and desserts at nearby maid cafes.");
            a3.setImagePath("img_akihabara");
            this.db.attractionDAO().addAttraction(a3);
            Attraction a4 = new Attraction();
            a4.setName("Life Sized Gundam Statue");
            a4.setAddress("1, Aomi, Koto City, Tokyo 135-0064, Japan");
            a4.setDescription("Giant white statue of cult sci-fi novel & anime character, with nighttime music & lights.");
            a4.setImagePath("img_unicorn_gundam_statue");
            this.db.attractionDAO().addAttraction(a4);
            Attraction a5 = new Attraction();
            a5.setName("Sensō-ji");
            a5.setAddress("2 Chome-3-1 Asakusa, Taito City, Tokyo 111-0032, Japan");
            a5.setDescription("Sensō-ji is an ancient Buddhist temple located in Asakusa, Tokyo, Japan. It is Tokyo's oldest temple, and one of its most significant. Formerly associated with the Tendai sect of Buddhism, it became independent after World War II.");
            a5.setImagePath("img_senso_ji");
            this.db.attractionDAO().addAttraction(a5);
//            Attraction a6 = new Attraction();
//            a6.setName("Meiji Jingu");
//            a6.setAddress("1-1 Yoyogikamizonocho, Shibuya City, Tokyo 151-8557, Japan");
//            a6.setDescription("Meiji Shrine, located in Shibuya, Tokyo, is the Shinto shrine that is dedicated to the deified spirits of Emperor Meiji and his wife, Empress Shōken. The shrine does not contain the emperor's grave, which is located at Fushimi-momoyama, south of Kyoto.");
//            a6.setImagePath("img_meiji_jingu");
//            this.db.attractionDAO().addAttraction(a6);
//            Attraction a7 = new Attraction();
//            a7.setName("Ueno Onshi Park");
//            a7.setAddress("Uenokoen, Taito City, Tokyo 110-0007, Japan");
//            a7.setDescription("Ueno Park is a spacious public park in the Ueno district of Taitō, Tokyo, Japan. The park was established in 1873 on lands formerly belonging to the temple of Kan'ei-ji.");
//            a7.setImagePath("img_ueno_onshi_park");
//            this.db.attractionDAO().addAttraction(a7);
        }
        Log.d("INIT_DB", ""+this.db.attractionDAO().getAttractionList().size());
    }
}
