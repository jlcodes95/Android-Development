package com.example.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PaySlipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slip);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ((TextView) findViewById(R.id.tvName)).setText(bundle.getString("name"));
        ((TextView) findViewById(R.id.tvEmployeeName)).setText("Employee Name: " + bundle.getString("name"));
        ((TextView) findViewById(R.id.tvEmployeeType)).setText("Employee Type: " + bundle.getString("employeeType"));
        ((TextView) findViewById(R.id.tvGrossSalary)).setText("Gross Salary: $" + bundle.getDouble("grossSalary"));
        ((TextView) findViewById(R.id.tvCIT)).setText("Canadian Income Tax: $" + bundle.getDouble("cit"));
        ((TextView) findViewById(R.id.tvNetSalary)).setText("Net Salary: $" + bundle.getDouble("netSalary"));
    }
}
