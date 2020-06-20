package com.example.labtest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CaseListActivity extends AppCompatActivity {

    private ListView lvCases;
    private CustomCaseListAdapter adapter;
    private ArrayList<Case> cases;
    private FirebaseFirestore db;
    private Spinner spProvinces;
    private ArrayList<String> provinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);

        db = FirebaseFirestore.getInstance();
        spProvinces = (Spinner) findViewById(R.id.spProvinces);
        provinces = new ArrayList<>();
        addProvinces();
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinces);
        spProvinces.setAdapter(spAdapter);

        spProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (provinces.get(position).contentEquals("All")) {
                    getData();
                }else {
                    getDataByProvince(provinces.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        lvCases = (ListView) findViewById(R.id.lvCases);
        cases = new ArrayList<>();
        adapter = new CustomCaseListAdapter(this, R.layout.case_list_layout, cases);
        lvCases.setAdapter(adapter);
        getData();
    }

    private void getData() {
        db.collection("cases")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cases.clear();
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                cases.add(document.toObject(Case.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void getDataByProvince(String province) {
        db.collection("cases")
                .whereEqualTo("province", province)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cases.clear();
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                cases.add(document.toObject(Case.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void addProvinces() {
        this.provinces.add("All");
        this.provinces.add("Alberta");
        this.provinces.add("British Columbia");
        this.provinces.add("Manitoba");
        this.provinces.add("New Brunswick");
        this.provinces.add("Newfoundland and Labrador");
        this.provinces.add("Northwest Territories");
        this.provinces.add("Nova Scotia");
        this.provinces.add("Nunavut");
        this.provinces.add("Ontario");
        this.provinces.add("Prince Edward Island");
        this.provinces.add("Quebec");
        this.provinces.add("Saskatchewan");
        this.provinces.add("Yukon");
    }

}
