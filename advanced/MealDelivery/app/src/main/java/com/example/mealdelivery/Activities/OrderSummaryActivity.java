package com.example.mealdelivery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealdelivery.Data.Order;
import com.example.mealdelivery.Data.OrderStatus;
import com.example.mealdelivery.Data.ParkingSlot;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderSummaryActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_ORDERSUMMARY";
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1998;
    private final float MIN_DISTANCE = 100;

    private final String ERR_MSG_FAILURE = "Something went wrong, please try again";
    private final String ERR_MSG_OUT_OF_RANGE = "You are not within 100m of the store, check in again when you are within 100m.";
    private final String ERR_MSG_CANNOT_PICKUP = "Your meal is not ready yet, please wait until the pickup date.";
    private final String MSG_PICKUP_READY = "Your next pickup will be ready on: ";
    private final String ERR_MSG_LOCATION_DENIED = "DENIED: Failed to request location";
    private final String MSG_LOCATION_REQUEST = "Location Permission Required";
    private final String MSG_LOCATION_RATIONALE = "Location permission is required to perform curb-side pickup.";
    private final String MSG_PARKING_TITLE = "Select parking spot";
    private final String MSG_PARKING_SPOTS = "Please enter 1, 2, or 3";

    private Order confirmedOrder;
    private boolean details;
    private boolean locationPermissionGranted;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        db = FirebaseFirestore.getInstance();

        try {
            Intent intent = getIntent();
            this.confirmedOrder = (Order) intent.getSerializableExtra("confirmedOrder");
            this.details = intent.getBooleanExtra("details", false);
            if (this.details) {
                ((Button) findViewById(R.id.btnBottom)).setText("Check In");
                addToolbarBackButton();
                setUpLocationManager();
            }
            loadData();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, ERR_MSG_FAILURE, Toast.LENGTH_LONG);
            finish();
        }
    }

    public void onBottomButtonPressed(View view) {
        if (!this.details) {
            final Intent intent = new Intent(this, DashboardActivity.class);
            //clears the stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            try {
                handleParking();
            }catch (Exception e) {
                Log.d(TAG, "No permission granted ");
            }
        }
    }

    private void handleParking() throws Exception{
        if (confirmedOrder.pickupAvailable()) {
            if (getLocation() <= MIN_DISTANCE) {
                showParkingDialog();
            }else {
                showCannotPickupDialog(ERR_MSG_OUT_OF_RANGE);
            }
        } else {
            showCannotPickupDialog(ERR_MSG_CANNOT_PICKUP);

        }
    }

    private void addToolbarBackButton() {
        Toolbar toolbar = findViewById(R.id.tbOrderSummary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        ((TextView) findViewById(R.id.tvOrderId)).setText(this.confirmedOrder.getOrderId());
        ((TextView) findViewById(R.id.tvItem)).setText(this.confirmedOrder.getSubscription().getName());
        ((TextView) findViewById(R.id.tvSubscriptionType)).setText(this.confirmedOrder.getSubscriptionType() == Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL ? "Semi-annual" : "Monthly");
        ((TextView) findViewById(R.id.tvPrice)).setText(this.confirmedOrder.getSubtotalDesc());
        ((TextView) findViewById(R.id.tvTax)).setText(this.confirmedOrder.getTaxDesc());
        ((TextView) findViewById(R.id.tvTotal)).setText(this.confirmedOrder.getTotalDesc());
        ((TextView) findViewById(R.id.tvFooter)).setText(MSG_PICKUP_READY + this.confirmedOrder.getPickupDateString());
    }

    private void setUpLocationManager() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                String message = String.format(
//                        "New Location \n Longitude: %1$s \n Latitude: %2$s",
//                        location.getLongitude(), location.getLatitude()
//                );
//                Log.d(TAG, message);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    handleParking();
                }catch (Exception e) {
                    Log.d(TAG, "No permission granted ");
                }
            }else {
                boolean showReason = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                if (showReason) {
                    showPermissionPopupRationale();
                }else {
                    Toast.makeText(this, ERR_MSG_LOCATION_DENIED, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showPermissionPopupRationale() {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        popup.setTitle(MSG_LOCATION_REQUEST)
                .setMessage(MSG_LOCATION_RATIONALE)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                })
                .setNegativeButton("NO", null);
        popup.show();
    }

    private float getLocation() throws Exception {
        float[] results = new float[1];

        //ask for permission to make phone call
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // now get the lat/lon from the location and do something with it.
            Log.d(TAG, "LOCATION: " + location.toString());
            LatLng store = new LatLng(43.6761, -79.4105);
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), store.latitude, store.longitude, results);
            Log.d(TAG, "distance between here and GBC is: " + results[0] + " meters");
            return results[0];
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //show rationale before request
            showPermissionPopupRationale();
        } else {
            // Show the default Android permissions popup box
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        throw new Exception("no permission granted");
    }

    private void showParkingDialog() {
        final EditText etSlot = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        etSlot.setLayoutParams(layoutParams);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(MSG_PARKING_TITLE);
        builder.setMessage(MSG_PARKING_SPOTS);
        builder.setView(etSlot);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

        //prevents dialog from disappearing after click
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean closeDialog = false;
                        String value = etSlot.getText().toString();
                        if (value.isEmpty()) {
                            etSlot.setError(MSG_PARKING_SPOTS);
                        }else {
                            try {
                                int number = Integer.parseInt(etSlot.getText().toString());
                                if (number != ParkingSlot.SLOT_1 && number != ParkingSlot.SLOT_2 && number != ParkingSlot.SLOT_3) {
                                    etSlot.setError(MSG_PARKING_SPOTS);
                                }else {
                                    dialog.dismiss();
                                    updateParkingSlot(number);
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                                etSlot.setError(MSG_PARKING_SPOTS);
                            }
                        }
                    }
                });
    }

    private void showCannotPickupDialog(String message) {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        popup.setTitle("Failed to checkin")
                .setMessage(message)
                .setPositiveButton("OK", null);
        popup.show();
    }

    private void updateParkingSlot(final int slot) {
        confirmedOrder.setOrderStatus(OrderStatus.WAITING_AT_PARKING);
        confirmedOrder.setNextPickupTime();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "user displayname: " + user.getDisplayName());
        final ParkingSlot parkingSlot = new ParkingSlot(user.getDisplayName(), confirmedOrder, slot);

        db.collection("parkingSlot")
                .whereEqualTo("slot", slot)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots.size() == 0) {
                            db.collection("parkingSlot")
                                    .add(parkingSlot)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            updateOrderStatusToParking();
                                        }
                                    });
                        }else {
                            db.collection("parkingSlot")
                                    .document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                    .set(parkingSlot)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            updateOrderStatusToParking();
                                        }
                                    });
                        }
                    }
                });
    }

    private void updateOrderStatusToParking() {
        db.collection("orders")
                .whereEqualTo("orderId", confirmedOrder.getOrderId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() > 0) {
                            db.collection("orders")
                                    .document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                    .set(confirmedOrder)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(OrderSummaryActivity.this, "COMPLETE", Toast.LENGTH_LONG);
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    });
                        }
                    }
                });
    }

}
