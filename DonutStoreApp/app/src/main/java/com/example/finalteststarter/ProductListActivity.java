package com.example.finalteststarter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// 1. Import the correct Toolbar package
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.finalteststarter.adapters.DonutListViewAdapter;
import com.example.finalteststarter.classes.Donut;
import com.example.finalteststarter.classes.DonutDatabase;

public class ProductListActivity extends AppCompatActivity {

    final String TAG = "DONUT";

    ListView productsListView;
    DonutListViewAdapter adapter;

//    ArrayList<Donut> donuts = new ArrayList<Donut>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // setup toolbar
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbarMovieListScreen);
        setSupportActionBar(t1);

        // add an up bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // create some movies and add to the arraylist
//        Donut d1 = new Donut("Chocolate Donut", "Donut with chocolate topping",3.50);
//        Donut d2 = new Donut("Vanilla Donut", "Donut with vanilla topping", 4.999);
//        Donut d3 = new Donut("Strawberry Donut", "Donut filled with strawberry jam", 2.50);
//
//        donuts.add(d1);
//        donuts.add(d2);
//        donuts.add(d3);
        DonutDatabase donutDatabase = Room.databaseBuilder(getApplicationContext(),
                DonutDatabase.class, "donutDatabase").allowMainThreadQueries().build();
        initEmptyDatabase(donutDatabase);
        List<Donut> donutList = donutDatabase.donutDAO().getDonutList();



        // DEBUG: print out the users to the terminal
//        Log.d(TAG, "The donuts in the system are:");
//        for (int i = 0; i < donuts.size(); i++) {
//            Donut d = donuts.get(i);
//            Log.d(TAG, i + ": " + d.getName());
//        }
//        Log.d(TAG, "-----");

        // add adapter
        adapter  =
                new DonutListViewAdapter(this,
                        R.layout.donuts_row_layout,
                        donutList);

        productsListView = (ListView) findViewById(R.id.lvDonuts);
        productsListView.setAdapter(adapter);

        // set click handler
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donut d = (Donut) productsListView.getItemAtPosition(position);
                Toast t = Toast.makeText(getApplicationContext(), "The price of " + d.getName() + " is: $" + d.getPrice(), Toast.LENGTH_SHORT);
                t.show();
            }
        });


    }

    // toobar functions
    // Tell the toolbar about your menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // tells android to load the res/menu/menu_main.xml
        // file as your tool bar menu items
        getMenuInflater().inflate(R.menu.toolbar_menu_layout, menu);
        return true;
    }

    // Create click handlers for your menu options
    // Control what happens when you click on each menu itme
    // item parameter contains the menu item the person clicked on
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 1. Figure out what menu option the person clicked on
        int selectedMenuItem = item.getItemId();

        switch(selectedMenuItem) {
            //@TODO: Handle menu items here
            case R.id.miLogout:
                Intent i = new Intent(ProductListActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
            default:
                // do nothing
        }

        // default android code
        return super.onOptionsItemSelected(item);
    }

    private void initEmptyDatabase(DonutDatabase donutDatabase){
        List<Donut> list = donutDatabase.donutDAO().getDonutList();

        //only initializes once when donut db is empty
        if (list.size() == 0){
            Donut d1 = new Donut();
            d1.setName("Chocolate Donut");
            d1.setDescription("Donut with chocolate topping");
            d1.setPrice(3.50);
            donutDatabase.donutDAO().addDonut(d1);

            Donut d2 = new Donut();
            d2.setName("Vanilla Donut");
            d2.setDescription("Donut with vanilla topping");
            d2.setPrice(4.999);
            donutDatabase.donutDAO().addDonut(d2);

            Donut d3 = new Donut();
            d3.setName("Strawberry Donut");
            d3.setDescription("Donut filled with strawberry jam");
            d3.setPrice(2.50);
            donutDatabase.donutDAO().addDonut(d3);
        }
    }


}
