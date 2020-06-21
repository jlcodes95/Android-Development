package com.example.mealdelivery.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealdelivery.Data.Order;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersViewHolder> {

    private final String TAG = "DEBUG_SUBSCRIPTIONS_ADAPTER";
    private ArrayList<Order> orders;
    IOnRowClickedListener listenerInterface;

    public MyOrdersAdapter(ArrayList<Order> orders, IOnRowClickedListener listener) {
        this.orders = orders;
        this.listenerInterface = listener;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // get the custom row layout
        View myOrdersRowView = inflater.inflate(R.layout.rv_layout_my_orders, parent, false);

        // Return the view holder
        MyOrdersViewHolder holder = new MyOrdersViewHolder(myOrdersRowView, listenerInterface);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        //get item from list
        Order order = orders.get(position);

        //display item in recyclerView
        TextView tvName = holder.getTvName();
        TextView tvOrderId = holder.getTvOrderId();
        TextView tvPickupDate = holder.getTvPickupDate();

        tvName.setText(order.getSubscription().getName());
        tvOrderId.setText(order.getOrderId());
        tvPickupDate.setText(order.getPickupDateString());
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

}
