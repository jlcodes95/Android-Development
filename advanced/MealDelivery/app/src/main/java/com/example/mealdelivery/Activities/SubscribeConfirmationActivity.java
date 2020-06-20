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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class SubscribeConfirmationActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_SUBSCRIBE";

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
            Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG);
            finish();
        }
    }

    private void loadData() {
        ((TextView) findViewById(R.id.tvItem)).setText(this.pendingOrder.getSubscription().getName());
        ((TextView) findViewById(R.id.tvSubscriptionType)).setText(this.pendingOrder.getSubscriptionType() == Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL ? "Semi-annual" : "Monthly");
        ((TextView) findViewById(R.id.tvPrice)).setText(this.pendingOrder.getSubtotalDesc());
        ((TextView) findViewById(R.id.tvTax)).setText(this.pendingOrder.getTaxDesc());
        ((TextView) findViewById(R.id.tvTotal)).setText(this.pendingOrder.getTotalDesc());
    }

    public void onPlaceOrderPressed(View view) {
        //@TODO: place order and store in db
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .add(pendingOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SubscribeConfirmationActivity.this, "Order successfully placed", Toast.LENGTH_LONG);
                        Intent intent = new Intent(SubscribeConfirmationActivity.this, OrderSummaryActivity.class);
                        intent.putExtra("confirmedOrder", pendingOrder);
                        startActivity(intent);
//                        final Intent intent = new Intent(SubscribeConfirmationActivity.this, DashboardActivity.class);
//                        //clears the stack
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                    }
                });
    }
}
