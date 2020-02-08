package com.example.testingmenubar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean g(MenuItem item){
        Log.d("MENU", ""+item.getTitle());
        Toast t;
        switch (item.getItemId()){
            case R.id.miAccountProfile:
                startActivity(new Intent(getApplicationContext(), AccountProfileActivity.class));
                return true;
            case R.id.miContact:
                t = Toast.makeText(getApplicationContext(), "You clicked on Contact", Toast.LENGTH_SHORT);
                t.show();
                return true;
            case R.id.miNewMessage:
                t = Toast.makeText(getApplicationContext(), "You clicked on Message", Toast.LENGTH_SHORT);
                t.show();
                return true;
            case R.id.miHelp:
                t = Toast.makeText(getApplicationContext(), "You clicked on Help", Toast.LENGTH_SHORT);
                t.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
