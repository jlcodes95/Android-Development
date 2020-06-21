package com.example.mealdelivery.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mealdelivery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupFragment extends Fragment {

    View view;

    public PickupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pickup, container, false);
        setFirstParkingSlow();
        setSecondParkingSlow();
        setThirdParkingSlow();
        //TODO: UPDATE PARKING DATA ON LOAD
        return view;
    }

    private void setFirstParkingSlow() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking1);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 1");
        ((TextView)linearLayout.findViewById(R.id.tvName)).setText("VACANT");
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText("");
    }

    private void setSecondParkingSlow() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking2);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 2");
    }

    private void setThirdParkingSlow() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking3);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 3");
        ((TextView)linearLayout.findViewById(R.id.tvName)).setText("VACANT");
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText("");
    }

}
