package com.example.objectintents;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reviews2Fragment extends Fragment {

    View view;
    Button button;


    public Reviews2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews2, container, false);
        view.setBackgroundColor(Color.RED);

        button = view.findViewById(R.id.btnTesting2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getContext(), "REVIEWS 2", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
