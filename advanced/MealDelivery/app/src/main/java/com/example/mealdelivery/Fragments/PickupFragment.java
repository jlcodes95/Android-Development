package com.example.mealdelivery.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mealdelivery.Data.ParkingSlot;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        setFirstParkingSlot();
        setSecondParkingSlot();
        setThirdParkingSlot();
        fetchData();
        return view;
    }

    private void setFirstParkingSlot() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking1);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 1");
        ((TextView)linearLayout.findViewById(R.id.tvName)).setText("VACANT");
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText("");
    }

    private void setSecondParkingSlot() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking2);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 2");
        ((TextView)linearLayout.findViewById(R.id.tvName)).setText("VACANT");
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText("");
    }

    private void setThirdParkingSlot() {
        LinearLayout linearLayout = view.findViewById(R.id.layoutParking3);
        ((TextView)linearLayout.findViewById(R.id.tvParkingSlot)).setText("Parking Slot 3");
        ((TextView)linearLayout.findViewById(R.id.tvName)).setText("VACANT");
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText("");
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("parkingSlot")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                            updateParkingSlot(document.toObject(ParkingSlot.class));
                        }
                    }
                });
    }

    private void updateParkingSlot(ParkingSlot parkingSlot) {
        LinearLayout linearLayout;
        switch(parkingSlot.getSlot()) {
            case ParkingSlot.SLOT_3:
                linearLayout = view.findViewById(R.id.layoutParking3);
                break;
            case ParkingSlot.SLOT_2:
                linearLayout = view.findViewById(R.id.layoutParking2);
                break;
            default:
                linearLayout = view.findViewById(R.id.layoutParking1);
        }

        ((TextView)linearLayout.findViewById(R.id.tvName)).setText(parkingSlot.getName());
        ((TextView)linearLayout.findViewById(R.id.tvOrderId)).setText(parkingSlot.getOrder().getOrderId());
    }

}
