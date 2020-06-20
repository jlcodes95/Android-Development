package com.example.mealdelivery.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealdelivery.Activities.SubscribeActivity;
import com.example.mealdelivery.Components.IOnRowClickedListener;
import com.example.mealdelivery.Components.SubscribeAdapter;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscribeFragment extends Fragment implements IOnRowClickedListener {

    private final String TAG = "DEBUG_FRAG_SUBSCRIBE";
    private final int CODE_SUBSCRIBE = 21;

    private View view;
    private RecyclerView rvSubscriptions;
    private ArrayList<Subscription> subscriptions;
    private SubscribeAdapter adapter;

    public SubscribeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subscribe, container, false);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        //set up components
        Log.d(TAG, "setting up rv");
        this.subscriptions = new ArrayList<>();
        this.rvSubscriptions = view.findViewById(R.id.rvSubscriptionsList);
        this.adapter = new SubscribeAdapter(subscriptions, this);
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
                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                            subscriptions.add(document.toObject(Subscription.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRowClicked(int position) {
        Log.d(TAG, "Clicked on " + this.subscriptions.get(position).getName());
        Intent intent = new Intent(getContext(), SubscribeActivity.class);
        intent.putExtra("subscription", this.subscriptions.get(position));
        startActivityForResult(intent, CODE_SUBSCRIBE);
    }

}
