package com.example.mealdelivery.Components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealdelivery.DataTransferObjects.SubscriptionDto;
import com.example.mealdelivery.R;

import java.util.ArrayList;

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {

    private final String TAG = "DEBUG_SUBSCRIPTIONS_ADAPTER";
    private ArrayList<SubscriptionDto> subscriptions;
    IOnRowClickedListener listenerInterface;

    public SubscriptionsAdapter(ArrayList<SubscriptionDto> subscriptions, IOnRowClickedListener listener) {
        this.subscriptions = subscriptions;
        this.listenerInterface = listener;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // get the custom row layout
        View subscriptionRowView = inflater.inflate(R.layout.rv_layout_subscriptions, parent, false);

        // Return the view holder
        SubscriptionViewHolder holder = new SubscriptionViewHolder(subscriptionRowView, listenerInterface);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        //get item from list
        SubscriptionDto subscription = subscriptions.get(position);

        //display item in recyclerView
        ImageView ivPhoto = holder.getIvPhoto();
        TextView tvName = holder.getTvName();
        TextView tvDescription = holder.getTvDescription();

        Log.d(TAG, ""+position);
        tvName.setText(subscription.getName());
        tvDescription.setText(subscription.getDescription());

        Glide.with(holder.getIvPhoto().getContext()).load(subscription.getPhotoUrl()).into(ivPhoto);
    }

    @Override
    public int getItemCount() {
        return this.subscriptions.size();
    }

}
