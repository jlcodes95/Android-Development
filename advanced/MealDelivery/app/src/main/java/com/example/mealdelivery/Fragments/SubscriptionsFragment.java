package com.example.mealdelivery.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mealdelivery.Activities.EditSubscriptionActivity;
import com.example.mealdelivery.Components.IOnRowClickedListener;
import com.example.mealdelivery.Components.SubscriptionsAdapter;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionsFragment extends Fragment implements IOnRowClickedListener {

    private final String TAG = "DEBUG_FRAG_SUBSCRIPTIONS";
    private final int CODE_EDIT_SUBSCRIPTION = 33;

    private View view;
    private RecyclerView rvSubscriptions;
    private ArrayList<String> documentIds;
    private ArrayList<Subscription> subscriptions;
    private SubscriptionsAdapter adapter;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.btnAddSubscription);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewSubscriptionPressed(v);
            }
        });
        button.setScaleType(ImageView.ScaleType.CENTER);
        setupRecyclerView();

        return view;
    }

    public void onNewSubscriptionPressed(View view) {
        Log.d(TAG, "new subscription pressed.");
        startActivityForResult(new Intent(getContext(), EditSubscriptionActivity.class), CODE_EDIT_SUBSCRIPTION);
    }

    private void setupRecyclerView() {
        //set up components
        Log.d(TAG, "setting up rv");
        this.documentIds = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.rvSubscriptions = view.findViewById(R.id.rvSubscriptionsList);
        this.adapter = new SubscriptionsAdapter(subscriptions, this);
        this.rvSubscriptions.setAdapter(this.adapter);
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchData();
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("subscriptions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "get all subscriptions");
                        subscriptions.clear();
                        documentIds.clear();
                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                            documentIds.add(document.getId());
                            subscriptions.add(document.toObject(Subscription.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRowClicked(int position) {
        Log.d(TAG, "Clicked on " + this.subscriptions.get(position).getName());
        Intent intent = new Intent(getContext(), EditSubscriptionActivity.class);
        intent.putExtra("existingSubscription", this.subscriptions.get(position));
        intent.putExtra("documentId", this.documentIds.get(position));
        startActivityForResult(intent, CODE_EDIT_SUBSCRIPTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_EDIT_SUBSCRIPTION) {
            fetchData();
        }
    }
}
