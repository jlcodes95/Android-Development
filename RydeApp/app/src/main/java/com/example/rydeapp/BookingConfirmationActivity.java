/* -----------------------------------
 * John Lin
 * 101296282
 * ----------------------------------- */

package com.example.rydeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rydeapp.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingConfirmationActivity extends AppCompatActivity {

    private static final String DEST_UNION = "Union Station";
    private static final String DEST_AIRPORT = "Pearson Airport";
    private static final String DEST_EATON = "Eaton Center";
    private static final String RYDE_DIRECT = "Ryde Direct";
    private static final String RYDE_POOL = "Ryde Pool";

    private static final double DISTANCE_AIRPORT = 22.5;
    private static final double DISTANCE_EATON = 1.3;

    private static final double BASE_FARE = 2.50;
    private static final double MINIMUM_FEE = 6.25;
    private static final double SERVICE_FEE = 1.75;
    private static final double PPK = 0.81;

    private static final double DISCOUNT = 0.10;
    private static final double SURGE = 0.20;

    private String destination;
    private String type;
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);


        setSupportActionBar((Toolbar) findViewById(R.id.tbConfirmation));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        TextView tvThankYou = (TextView) findViewById(R.id.tvThankYou);
        TextView tvPhoneConfirmation = (TextView) findViewById(R.id.tvPhoneConfirmation);

        tvThankYou.setText("Thanks " + bundle.getString("username") + "!");
        tvPhoneConfirmation.setText("The driver will call you at " + bundle.getString("phoneNumber"));
        this.destination = bundle.getString("to");
        this.type = bundle.getString("type");
        doFinalOperations();
    }

    private void doFinalOperations(){
        TextView tvEstimatedFee = (TextView) findViewById(R.id.tvEstimatedFee);
        TextView tvFrom = (TextView) findViewById(R.id.tvFrom);
        TextView tvTo = (TextView) findViewById(R.id.tvTo);
        TextView tvDistance = (TextView) findViewById(R.id.tvDistance);
        TextView tvPPK = (TextView) findViewById(R.id.tvPPK);
        TextView tvBookingFee = (TextView) findViewById((R.id.tvBookingFee));
        TextView tvServiceCharge = (TextView) findViewById(R.id.tvServiceCharge);
        TextView tvDistanceCharge = (TextView) findViewById(R.id.tvDistanceCharge);

        tvFrom.setText("From: " + DEST_UNION);
        tvTo.setText("To: " + this.destination);
        tvServiceCharge.setText("Service Charge: $" + df.format(SERVICE_FEE));
        tvBookingFee.setText("Booking Fee: $" + df.format(BASE_FARE));

        //calculating distance charge
        double distanceCharge = 0;
        double ppk = PPK;
        if (isDuringPeakHours()){
            ppk *= (1+SURGE);
        }

        if (this.destination.equals(DEST_AIRPORT)){
            tvDistance.setText("Distance: " + DISTANCE_AIRPORT + "km");
            distanceCharge = ppk * DISTANCE_AIRPORT;
        }else{
            tvDistance.setText("Distance: " + DISTANCE_EATON + "km");
            distanceCharge = ppk * DISTANCE_EATON;
        }
        tvPPK.setText("Price per km: $" + ppk + "/km");

        if (type.equals(RYDE_POOL)){
            distanceCharge *= (1 - DISCOUNT);
        }

        tvDistanceCharge.setText("Distance Charge: $" + df.format(distanceCharge));
        double estimatedFee = distanceCharge + BASE_FARE + SERVICE_FEE;
        if (estimatedFee < MINIMUM_FEE){
            estimatedFee = MINIMUM_FEE;
        }

        tvEstimatedFee.setText("$" + df.format(estimatedFee));

    }

    public void onClickHangleLogout(View view){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private boolean isDuringPeakHours() {
        while(true){
            try{
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.format(date);
                // breaks & return true if date (current) is between 16:00 and 18:00
                return dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("16:00")) &&
                        dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("18:00"));
            }catch (ParseException e){
                Toast t = Toast.makeText(this, "Attempting to get current time...", Toast.LENGTH_SHORT);
                t.show();
            }
        }


    }
}
