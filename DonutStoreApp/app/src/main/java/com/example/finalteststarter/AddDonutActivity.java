package com.example.finalteststarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalteststarter.classes.Donut;
import com.example.finalteststarter.classes.DonutDatabase;

public class AddDonutActivity extends AppCompatActivity {

    final String TAG = "DONUT";
    private DonutDatabase donutDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donut);

        donutDatabase = Room.databaseBuilder(getApplicationContext(),
                DonutDatabase.class, "donutDatabase").allowMainThreadQueries().build();

    }

    public void saveDonutButtonPressed(View view) {

        // @TODO: Put your code here
        Toast t = Toast.makeText(getApplicationContext(), "Donut saved!", Toast.LENGTH_SHORT);

        EditText etName = findViewById(R.id.etAddDonutName);
        EditText etDescription = findViewById(R.id.etAddDonutDescription);
        EditText etPrice = findViewById(R.id.etAddDonutPrice);

        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String price = etPrice.getText().toString();

        if (name.equals("")){
            t.setText("Please enter a donut name");
        }else if (description.equals("")){
            t.setText("Please enter a description");
        }else if (price.equals("")){
            t.setText("Please enter a price");
        }else{
            //all data is inputted
            Donut donut = new Donut();
            donut.setName(name);
            donut.setDescription(description);
            try{
                donut.setPrice(Double.parseDouble(price));
                donutDatabase.donutDAO().addDonut(donut);
            }catch(NumberFormatException e){
                t.setText("Please enter a valid price!");
            }catch(Exception e){
                t.setText("There's already a donut named " + name + " , please use another");
            }
        }
        t.show();

    }
}
