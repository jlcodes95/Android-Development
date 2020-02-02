/* -----------------------------------
 * John Lin
 * 101296282
 * ----------------------------------- */
package com.example.rydeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DEST_UNION = "Union Station";
    private static final String DEST_AIRPORT = "Pearson Airport";
    private static final String DEST_EATON = "Eaton Center";

    private static final String RYDE_DIRECT = "Ryde Direct";
    private static final String RYDE_POOL = "Ryde Pool";



    private ArrayList<String> FROM_OPTIONS = new ArrayList<String>();
    private ArrayList<String> TO_OPTIONS = new ArrayList<String>();

    private String username;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set spinner array values;
        FROM_OPTIONS.add(DEST_UNION);
        TO_OPTIONS.add(DEST_AIRPORT);
        TO_OPTIONS.add(DEST_EATON);

        Spinner fromSpinner = (Spinner) findViewById(R.id.spFrom);
        Spinner toSpinner = (Spinner) findViewById(R.id.spTo);

        //setting resource for from spinner
//        fromSpinner.setAdapter(null);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FROM_OPTIONS);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(spinnerArrayAdapter);


        //setting resource for to spinner
//        fromSpinner.setAdapter(null);
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TO_OPTIONS);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(spinnerArrayAdapter);

        Bundle bundle = (getIntent().getExtras());
        this.username = bundle.getString("username");
        this.phoneNumber = bundle.getString("phoneNumber");

        TextView tvGreetings = (TextView) findViewById(R.id.tvGreeting);
        tvGreetings.setText("Hello " + username + ".");
    }

    public void onClickHandlePool(View view){
        handleIntentAndBundling(RYDE_POOL);
    }

    public void onClickHandleDirect(View view){
        handleIntentAndBundling(RYDE_DIRECT);
    }

    private void handleIntentAndBundling(String type){
        Spinner spFrom = (Spinner) findViewById(R.id.spFrom);
        Spinner spTo = (Spinner) findViewById(R.id.spTo);

        String from = spFrom.getSelectedItem().toString();
        String to = spTo.getSelectedItem().toString();

        Intent confirmationIntent = new Intent(this, BookingConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("username", this.username);
        bundle.putString("phoneNumber", this.phoneNumber);
        bundle.putString("from", from);
        bundle.putString("to", to);
        confirmationIntent.putExtras(bundle);

        startActivity(confirmationIntent);
    }

}
