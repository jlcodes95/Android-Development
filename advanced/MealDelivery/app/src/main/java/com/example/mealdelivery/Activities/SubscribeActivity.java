package com.example.mealdelivery.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealdelivery.Data.Order;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SubscribeActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_SUBSCRIBE";
    private final String ERR_MSG_FAILED = "Something went wrong, please try again";
    private final String LABEL_SELECT = "SELECT";
    private final String LABEL_SELECTED = "SELECTED";

    private LinearLayout layoutMonthly;
    private LinearLayout layoutSA;
    private Subscription subscription;
    private int subscriptionType = Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Toolbar toolbar = findViewById(R.id.tbSubscribe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layoutMonthly = findViewById(R.id.layoutMonthly);
        layoutSA = findViewById(R.id.layoutSA);
        onSASelected(layoutSA);

        try {
            this.subscription = (Subscription) getIntent().getSerializableExtra("subscription");
            loadData();
        }catch (Exception e){
            Toast.makeText(this, ERR_MSG_FAILED, Toast.LENGTH_LONG);
            finish();
        }
    }

    public void onMonthlySelected(View view) {
        Log.d(TAG, "onMonthlySelected");
        this.subscriptionType = Subscription.COUNT_SUBSCRIPTION_MONTH;
        ((TextView) findViewById(R.id.tvMonthSelect)).setText(LABEL_SELECTED);
        ((TextView) findViewById(R.id.tvSASelect)).setText(LABEL_SELECT);
        layoutMonthly.setBackgroundResource(R.color.colorAccent);
        layoutSA.setBackgroundColor(Color.WHITE);
    }

    public void onSASelected(View view) {
        Log.d(TAG, "onSASelected");
        this.subscriptionType = Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL;
        ((TextView) findViewById(R.id.tvMonthSelect)).setText(LABEL_SELECT);
        ((TextView) findViewById(R.id.tvSASelect)).setText(LABEL_SELECTED);
        layoutSA.setBackgroundResource(R.color.colorAccent);
        layoutMonthly.setBackgroundColor(Color.WHITE);
    }

    public void onNextPressed(View view) {
        Log.d(TAG, "onNextPressed");
        Order pendingOrder = new Order(FirebaseAuth.getInstance().getUid(), this.subscriptionType, this.subscription);
        Intent intent = new Intent(this, SubscribeConfirmationActivity.class);
        intent.putExtra("pendingOrder", pendingOrder);
        startActivity(intent);
    }

    private void loadData() {
        //monthly
        ((TextView) findViewById(R.id.tvMonthPrice)).setText(this.subscription.getTotalDesc(Subscription.COUNT_SUBSCRIPTION_MONTH) + " paid monthly");
        ((TextView) findViewById(R.id.tvMonthMealPrice)).setText(this.subscription.getPlanDesc(Subscription.COUNT_SUBSCRIPTION_MONTH));

        //semi annually
        ((TextView) findViewById(R.id.tvSAPrice)).setText(this.subscription.getTotalDesc(Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL) + " semi-annually");
        ((TextView) findViewById(R.id.tvSAMealPrice)).setText(this.subscription.getPlanDesc(Subscription.COUNT_SUBSCRIPTION_SEMI_ANNUAL));

        ((TextView) findViewById(R.id.tvDescription)).setText(this.subscription.getDescription());

        //image
        ImageView ivBackground = findViewById(R.id.ivBackground);
        Glide.with(this).load(this.subscription.getPhotoUrl()).into(ivBackground);

        //sample images
        LinearLayout layoutSamplePhoto = findViewById(R.id.layoutSamplePhoto);
        for (String url: this.subscription.getSamplePhotoUrls()) {
            ImageView photo = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.sample_photo_width_subscribe),
                    (int) getResources().getDimension(R.dimen.sample_photo_height_subscribe));
            photo.setLayoutParams(layoutParams);
            photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photo.setPadding(5, 5, 5, 5);
            Glide.with(this).load(url).into(photo);
            layoutSamplePhoto.addView(photo);
        }
    }
}
