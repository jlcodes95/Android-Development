package com.example.mealdelivery.Components;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealdelivery.R;

public class SubscriptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final String TAG = "DEBUG_SUBSCRIPTION_VIEWHOLDER";
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;
    IOnRowClickedListener listenerInterface;

    public SubscriptionViewHolder(View itemView, IOnRowClickedListener listenerInterface) {
        super(itemView);

        this.ivPhoto = itemView.findViewById(R.id.ivPhoto);
        this.tvName = itemView.findViewById(R.id.tvName);
        this.tvDescription = itemView.findViewById(R.id.tvDescription);

        this.listenerInterface = listenerInterface;
        itemView.setOnClickListener(this);
    }

    public ImageView getIvPhoto() {
        return ivPhoto;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Clicked on: " + getAdapterPosition());
        this.listenerInterface.onRowClicked(getAdapterPosition());
    }
}
