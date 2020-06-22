package com.example.mealdelivery.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealdelivery.Data.Order;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SubscribeConfirmationActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_SUBSCRIBE";
    private final String ERR_MSG_FAILED = "Something went wrong, please try again";
    private final String LABEL_SA = "Semi-annual";
    private final String LABEL_MONTHLY = "Monthly";
    private final String SUCC_MSG_ORDER_PLACED = "Order successfully placed";

    private Order pendingOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_confirmation);

        Toolbar toolbar = findViewById(R.id.tbSubscribeConfirmation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            this.pendingOrder = (Order) getIntent().getSerializableExtra("pendingOrder");
            loadData();
        }catch (Exception e){
            Toast.makeText(this, ERR_MSG_FAILED, Toast.LENGTH_LONG);
            finish();
        }
    }

    private void loadData() {
        ((TextView) findViewById(R.id.tvItem)).setText(this.pendingOrder.getSubscription().getName());
        ((TextView) findViewById(R.id.tvSubscriptionType)).setText(this.pendingOrder.getSubscriptionType() == Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL ? LABEL_SA : LABEL_MONTHLY);
        ((TextView) findViewById(R.id.tvPrice)).setText(this.pendingOrder.getSubtotalDesc());
        ((TextView) findViewById(R.id.tvTax)).setText(this.pendingOrder.getTaxDesc());
        ((TextView) findViewById(R.id.tvTotal)).setText(this.pendingOrder.getTotalDesc());
    }

    public void onPlaceOrderPressed(View view) {
        this.pendingOrder.setDefaultDates();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .add(pendingOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SubscribeConfirmationActivity.this, SUCC_MSG_ORDER_PLACED, Toast.LENGTH_LONG);
                        Intent intent = new Intent(SubscribeConfirmationActivity.this, OrderSummaryActivity.class);
                        intent.putExtra("confirmedOrder", pendingOrder);
                        startActivity(intent);
                    }
                });
    }
}
