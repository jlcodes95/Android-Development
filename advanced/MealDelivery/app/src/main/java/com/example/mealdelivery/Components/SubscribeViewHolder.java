package com.example.mealdelivery.Components;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealdelivery.R;

public class SubscribeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final String TAG = "DEBUG_SUBSCRIBE_VIEWHOLDER";
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvPrice;
    IOnRowClickedListener listenerInterface;

    public SubscribeViewHolder(View itemView, IOnRowClickedListener listenerInterface) {
        super(itemView);

        this.ivPhoto = itemView.findViewById(R.id.ivPhoto);
        this.tvName = itemView.findViewById(R.id.tvName);
        this.tvDescription = itemView.findViewById(R.id.tvDescription);
        this.tvPrice = itemView.findViewById(R.id.tvPrice);

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

    public TextView getTvPrice() {
        return tvPrice;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Clicked on: " + getAdapterPosition());
        this.listenerInterface.onRowClicked(getAdapterPosition());
    }
}
