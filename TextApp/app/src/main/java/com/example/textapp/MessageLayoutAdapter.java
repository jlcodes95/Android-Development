package com.example.textapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessageLayoutAdapter extends ArrayAdapter<Message> {

    private Context context;
    private int resource;
    private ArrayList<Message> objects;

    public MessageLayoutAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Message> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message m = this.objects.get(position);

        //create layout inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView tvAddress = convertView.findViewById(R.id.tvAddress);
        TextView tvMessage = convertView.findViewById(R.id.tvMessage);

        tvAddress.setText(m.getContactName());
        tvMessage.setText(m.getMessagePreview());

        tvMessage.setText(m.getMessage());
        if (m.getType() == Telephony.Sms.MESSAGE_TYPE_FAILED){
            tvMessage.setTextColor(Color.RED);
        }

        if (!m.isRead()){
            tvAddress.setTypeface(Typeface.DEFAULT_BOLD);
            tvMessage.setTypeface(Typeface.DEFAULT_BOLD);
        }


        return convertView;

    }
}
