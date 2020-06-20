package com.example.labtest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ViewCaseActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_VIEWCASE";
    private ListView lvViewCases;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> documentIds;
    private ArrayList<String> provinces;
    FirebaseFirestore db;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case);
        lvViewCases = (ListView) findViewById(R.id.lvViewCases);
        documentIds = new ArrayList<>();
        provinces = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, provinces);
        lvViewCases.setAdapter(adapter);
        lvViewCases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewCaseActivity.this, AddCaseActivity.class);
                intent.putExtra("documentId", documentIds.get(position));
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        getProvinces();
    }

    private void getProvinces() {
        db.collection("cases")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                documentIds.add(document.getId());
                                provinces.add(document.toObject(Case.class).getProvince());
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            Log.d(TAG, "something went wrong");
                        }
                    }
                });
    }
}
