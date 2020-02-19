package com.example.finalteststarter.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.finalteststarter.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment {

    View view;

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        ((WebView) view.findViewById(R.id.wvFAQ)).loadUrl("file:///android_asset/faq.html");
        // Inflate the layout for this fragment
        return view;
    }

}
