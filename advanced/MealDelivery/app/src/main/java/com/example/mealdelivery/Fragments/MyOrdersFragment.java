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
import com.example.mealdelivery.Activities.OrderSummaryActivity;
import com.example.mealdelivery.Components.IOnRowClickedListener;
import com.example.mealdelivery.Components.MyOrdersAdapter;
import com.example.mealdelivery.Components.SubscriptionsAdapter;
import com.example.mealdelivery.Data.Order;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment implements IOnRowClickedListener {

    private final String TAG = "DEBUG_FRAG_MYORDERS";
    private final int ORDER_SUMMARY_CODE = 55;

    private View view;
    private RecyclerView rvMyOrders;
    private ArrayList<Order> orders;
    private MyOrdersAdapter adapter;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        //set up components
        Log.d(TAG, "setting up rv");
        this.orders = new ArrayList<>();
        this.rvMyOrders = view.findViewById(R.id.rvMyOrders);
        this.adapter = new MyOrdersAdapter(orders, this);
        this.rvMyOrders.setAdapter(this.adapter);
        this.rvMyOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchData();
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .whereEqualTo("orderedBy", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "get all orders");
                        orders.clear();
                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                            orders.add(document.toObject(Order.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRowClicked(int position) {
        Log.d(TAG, "Clicked on " + position);
        Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
        intent.putExtra("details", true);
        intent.putExtra("confirmedOrder", this.orders.get(position));
        startActivityForResult(intent, ORDER_SUMMARY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fetchData();
    }
}
