package com.example.labtest1;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final String TAG = "DEBUG_VIEWHOLDER";

    private TextView tvFullName;
    private TextView tvCoordinates;

    IOnRowClickedListener listenerInterface;

    public ViewHolder(View itemView, IOnRowClickedListener listenerInterface) {
        super(itemView);

        this.tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
        this.tvCoordinates = (TextView) itemView.findViewById(R.id.tvCoordinates);

        this.listenerInterface = listenerInterface;
        itemView.setOnClickListener(this);
    }

    public TextView getTvFullName() {
        return tvFullName;
    }

    public TextView getTvCoordinates() {
        return tvCoordinates;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Clicked on: " + getAdapterPosition());
        this.listenerInterface.onRowClicked(getAdapterPosition());
    }
}

