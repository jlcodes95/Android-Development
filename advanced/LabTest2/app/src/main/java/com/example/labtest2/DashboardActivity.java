package com.example.labtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_DASHBOARD";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra("user");
        Log.d(TAG, user.getFirstName());

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome " + this.user.getFullName());
    }

    public void onAddCasePressed(View view) {
        Log.d(TAG, "add cases pressed");
        startActivity(new Intent(this, AddCaseActivity.class));
    }

    public void onViewCasesPressed(View view) {
        Log.d(TAG, "view cases pressed");
        startActivity(new Intent(this, ViewCaseActivity.class));
    }
}
