package com.example.tourismapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tourismapp.R;
import com.example.tourismapp.activities.MainActivity;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.AttractionListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionsFragment extends Fragment {

    View view;

    public AttractionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_attractions, container, false);
        loadAttractions();
        return view;
    }

    private void loadAttractions(){
        List<Attraction> list = MainActivity.db.attractionDAO().getAttractionList();
        ListView lv = view.findViewById(R.id.lvAttractions);
        AttractionListAdapter adapter = new AttractionListAdapter(getContext(), R.layout.layout_attraction, list);
        lv.setAdapter(adapter);
    }

}
