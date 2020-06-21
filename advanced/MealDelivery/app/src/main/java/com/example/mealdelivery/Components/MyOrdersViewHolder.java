package com.example.mealdelivery.Components;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealdelivery.R;

public class MyOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final String TAG = "DEBUG_MYSUBSCRIPTIONS_VIEWHOLDER";
    private TextView tvName;
    private TextView tvOrderId;
    private TextView tvPickupDate;
    IOnRowClickedListener listenerInterface;

    public MyOrdersViewHolder(View itemView, IOnRowClickedListener listenerInterface) {
        super(itemView);

        this.tvName = itemView.findViewById(R.id.tvName);
        this.tvOrderId = itemView.findViewById(R.id.tvOrderId);
        this.tvPickupDate = itemView.findViewById(R.id.tvPickupDate);

        this.listenerInterface = listenerInterface;
        itemView.setOnClickListener(this);
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvOrderId() {
        return tvOrderId;
    }

    public TextView getTvPickupDate() {
        return tvPickupDate;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Clicked on: " + getAdapterPosition());
        this.listenerInterface.onRowClicked(getAdapterPosition());
    }
}
