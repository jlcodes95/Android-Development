package com.example.tourismapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.tourismapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OlympicsFragment extends Fragment {

    View view;
    private static final String DEFAULT_OLYMPICS_URL = "https://tokyo2020.org/en/games/schedule/olympic/";
    public OlympicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_olympics, container, false);
        ((WebView) view.findViewById(R.id.wvOlympics)).loadUrl(DEFAULT_OLYMPICS_URL);
        return view;
    }

}
