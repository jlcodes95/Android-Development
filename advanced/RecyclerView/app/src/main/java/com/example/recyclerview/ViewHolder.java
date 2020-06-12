package com.example.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView image;
    TextView name;
    IOnRowClickedListener listenerInterface;

    public ViewHolder(View itemView, IOnRowClickedListener listenerInterface) {
        super(itemView);
        this.image = itemView.findViewById(R.id.imgPhoto);
        this.name = itemView.findViewById(R.id.tvName);
        this.listenerInterface = listenerInterface;
        itemView.setOnClickListener(this);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    @Override
    public void onClick(View v) {
        Log.d("DBUG", "Clicked on: " + getAdapterPosition());
        this.listenerInterface.onRowClicked(getAdapterPosition());
    }
}
