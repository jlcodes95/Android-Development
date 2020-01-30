package com.example.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCalculate(View view){
        try{
            EditText etName = (EditText) findViewById(R.id.etName);
            EditText etHours = (EditText) findViewById(R.id.etHours);
            RadioButton rbRegular = (RadioButton) findViewById(R.id.rbRegular);
            RadioButton rbFaculty = (RadioButton) findViewById(R.id.rbFaculty);

            String name = etName.getText().toString();
            String hourString = etHours.getText().toString();

            if(name.equals("")){
                throw new Exception("Please enter a name.");
            }else if (hourString.equals("")){
                throw new Exception("Please enter hours worked.");
            }

            Intent paySlipIntent = new Intent(this, PaySlipActivity.class);
            Bundle paySlipBundle = new Bundle();

            paySlipBundle.putString("name", name);

            boolean isRegular = false;
            if (rbRegular.isChecked()){
                isRegular = true;
                paySlipBundle.putString("employeeType", rbRegular.getText().toString());
            }else if (rbFaculty.isChecked()){
                isRegular = false;
                paySlipBundle.putString("employeeType", rbFaculty.getText().toString());
            }else{
                throw new Exception("Please select employee type.");
            }

            double hours = Double.parseDouble(hourString);

            double grossSalary, cit, netSalary;
            if (isRegular){
                grossSalary = hours * 25;
            }else{
                grossSalary = hours * 90.00;
            }

            if (grossSalary <= 1900.00){
                cit = 0;
            }else{
                cit = (grossSalary - 1900.00) * 0.25;
            }

            netSalary = grossSalary - cit;

            paySlipBundle.putDouble("grossSalary", grossSalary);
            paySlipBundle.putDouble("cit", cit);
            paySlipBundle.putDouble("netSalary", netSalary);

            paySlipIntent.putExtras(paySlipBundle);
            startActivity(paySlipIntent);


        }catch (Exception e){
            Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }

    }
}
