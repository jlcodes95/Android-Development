package com.example.employeelistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.tbDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            ((TextView) findViewById(R.id.tvName)).setText(bundle.getString("name"));
            ((TextView) findViewById(R.id.tvDepartment)).setText(bundle.getString("department"));
            ((TextView) findViewById(R.id.tvGender)).setText(bundle.getString("gender"));
            ((TextView) findViewById(R.id.tvSalary)).setText("$" + bundle.getDouble("salary"));
        }
    }
}
