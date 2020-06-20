package com.example.labtest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddCaseActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_ADDCASE";

    private Case editCase;
    private String documentId = null;
    private EditText etTotalCases;
    private EditText etTotalRecovered;
    private EditText etTotalDeaths;
    private Spinner spProvinces;

    private ArrayList<String> provinces;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        etTotalCases = (EditText) findViewById(R.id.etTotalCases);
        etTotalRecovered = (EditText) findViewById(R.id.etTotalRecovered);
        etTotalDeaths = (EditText) findViewById(R.id.etTotalDeaths);
        spProvinces = (Spinner) findViewById(R.id.spProvinces);

        provinces = new ArrayList<>();
        addProvinces();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinces);
        spProvinces.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        //is editing check
        Intent intent = getIntent();
        if (intent.getStringExtra("documentId") != null) {
            Log.d(TAG, "editing...");
            documentId = intent.getStringExtra("documentId");
            getExistingCaseById(documentId);
        }
    }

    public void onAddCasePressed(View view) {
        String totalCases = etTotalCases.getText().toString();
        String totalRecovered = etTotalRecovered.getText().toString();
        String totalDeaths = etTotalDeaths.getText().toString();
        String province = (String) spProvinces.getSelectedItem();

        if (totalCases.isEmpty()) {
            etTotalCases.setError("Please enter total cases");
        }else if (totalRecovered.isEmpty()){
            etTotalRecovered.setError("Please enter total recovered");
        }else if (totalDeaths.isEmpty()) {
            etTotalDeaths.setError("Please enter total deaths");
        }else if (province == null || province.toString().contentEquals("")) {
            Toast t = Toast.makeText(this, "Please select a province to continue", Toast.LENGTH_LONG);
            t.show();
        }else {
            //add to db
            try {
                Log.d(TAG, "attempting to add to db");
                Case currentCase = editCase;
                if (editCase == null) {
                    currentCase = new Case(Integer.parseInt(totalCases), Integer.parseInt(totalRecovered), Integer.parseInt(totalDeaths), province.toString());
                    addCase(currentCase);
                }else {
                    currentCase.setTotalCases(Integer.parseInt(totalCases));
                    currentCase.setTotalRecovered(Integer.parseInt(totalRecovered));
                    currentCase.setTotalDeaths(Integer.parseInt(totalDeaths));
                    updateCase(currentCase);
                }
            }catch (NumberFormatException e) {
                Toast t = Toast.makeText(this, "Please make sure the count of each field are numbers", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    private void getExistingCaseById(String documentId) {
        db.collection("cases")
                .document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            editCase = task.getResult().toObject(Case.class);
                            etTotalCases.setText(""+editCase.getTotalCases());
                            etTotalRecovered.setText(""+editCase.getTotalRecovered());
                            etTotalDeaths.setText(""+editCase.getTotalDeaths());
                            spProvinces.setSelection(provinces.indexOf(editCase.getProvince()));
                            spProvinces.setEnabled(false);

                            //change text
                            Button button = (Button) findViewById(R.id.btnAddCase);
                            button.setText("Update Case");
                            TextView textView = (TextView) findViewById(R.id.tvAddCase);
                            textView.setText("Update Case");
                        }
                    }
                });
    }

    private void addCase(Case newCase) {
        db.collection("cases")
                .add(newCase)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast t = Toast.makeText(AddCaseActivity.this, "Added!", Toast.LENGTH_LONG);
                        t.show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void updateCase(Case existingCase) {
        db.collection("cases").document(documentId).set(existingCase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast t = Toast.makeText(AddCaseActivity.this, "Updated", Toast.LENGTH_LONG);
                    t.show();
                    finish();
                }
            }
        });
    }

    private void addProvinces() {
        this.provinces.add("");
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
