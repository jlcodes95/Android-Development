package com.example.tourismapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.TouristDatabase;

public class AddAttractionActivity extends AppCompatActivity {

    private static final String ERR_MSG_EMPTY_NAME = "Name cannot be empty";
    private static final String ERR_MSG_EMPTY_ADDRESS = "Address cannot be empty";
    private static final String ERR_MSG_EMPTY_DESCRIPTION = "Description cannot be empty";
    private static final String ERR_MSG_EMPTY_IMAGE_PATH = "Image path cannot be empty";
    private static final String SUCCESS_MSG =  "Successfully added a new attraction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attraction);

        setSupportActionBar((Toolbar) findViewById(R.id.tbNewAttraction));
    }

    /**
     * add attractions menu creation
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_attraction, menu);
        return true;
    }

    /**
     * add attractions menu item selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.miLogout:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }

    /**
     * handle adding attraction to db
     * @param view
     */
    public void onClickAddAttraction(View view){
        EditText etName = findViewById(R.id.etName);
        EditText etAddress = findViewById(R.id.etAddress);
        EditText etDescription = findViewById(R.id.etDescriptions);
        EditText etImagePath = findViewById(R.id.etImagePath);

        String name = etName.getText().toString();
        String address = etAddress.getText().toString();
        String description = etDescription.getText().toString();
        String imagePath = etImagePath.getText().toString();

        try{
            if (name.equals("")){
                etName.setError(ERR_MSG_EMPTY_NAME);
                throw new Exception(ERR_MSG_EMPTY_NAME);
            }else if (address.equals("")){
                etAddress.setError(ERR_MSG_EMPTY_ADDRESS);
                throw new Exception(ERR_MSG_EMPTY_ADDRESS);
            }else if (description.equals("")){
                etDescription.setError(ERR_MSG_EMPTY_DESCRIPTION);
                throw new Exception(ERR_MSG_EMPTY_DESCRIPTION);
            }else if (imagePath.equals("")){
                etImagePath.setError(ERR_MSG_EMPTY_IMAGE_PATH);
                throw new Exception(ERR_MSG_EMPTY_IMAGE_PATH);
            }

            Attraction attraction = new Attraction();
            attraction.setName(name);
            attraction.setAddress(address);
            attraction.setDescription(description);
            attraction.setImagePath(imagePath);

            MainActivity.db.attractionDAO().addAttraction(attraction);
            Toast t = Toast.makeText(this, SUCCESS_MSG, Toast.LENGTH_SHORT);
            t.show();

            etName.setText("");
            etAddress.setText("");
            etDescription.setText("");
            etImagePath.setText("");

        }catch(Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }

    }


}
