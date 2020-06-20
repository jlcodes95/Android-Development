package com.example.mealdelivery.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealdelivery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySubscriptionsFragment extends Fragment {

    View view;

    public MySubscriptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_subscriptions, container, false);
        return view;
    }

}
