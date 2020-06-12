package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private final String TAG = "DBUG_DASHBOARD";
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText etTask;
    private ListView lvTasks;
    private ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        etTask = (EditText) findViewById(R.id.etTask);
        lvTasks = findViewById(R.id.lvTasks);
        loadTasks();
    }

    public void onAddTaskPressed(View view) {
        Log.d(TAG, "Attempting to add task...");
        if (!etTask.getText().toString().isEmpty()) {
            Map<String, Object> task = new HashMap<>();
            task.put("Task Title", etTask.getText().toString());
            task.put("Date", LocalDate.now().toString());
            addTaskToDb(task);
        }
    }

    public void addTaskToDb(Map<String, Object> task) {
        db.collection("tasks")
                .add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        loadTasks();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void onLogoutPressed(View view){
        auth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void loadTasks(){
        tasks = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        lvTasks.setAdapter(adapter);
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            tasks.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                tasks.add(document.getData().get("Task Title").toString());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        detectItemClick();
    }

    public void detectItemClick() {
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item #" + position);
            }
        });
    }
}
