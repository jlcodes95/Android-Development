package com.example.tourismapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tourismapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OlympicsFragment extends Fragment {

    private View view;
    private String selectedDate;
    private static final String DEFAULT_OLYMPICS_URL = "https://tokyo2020.org/en/games/schedule/olympic/";
    private static final String SPECIFIC_OLYMPICS_URL = "https://tokyo2020.org/en/games/schedule/olympic/%s.html ";
    private static final String TAG = "OLYMPICS_FRAGMENT";

    public OlympicsFragment() {
        // Required empty public constructor
        this.selectedDate = null;
    }

    public OlympicsFragment(String selectedDate) {
        // Required empty public constructor
        this.selectedDate = selectedDate;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_olympics, container, false);
        WebView wv = view.findViewById(R.id.wvOlympics);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return false;
            }
        });
        if (this.selectedDate == null){
            wv.loadUrl(DEFAULT_OLYMPICS_URL);
        }else{
            Log.d(TAG, String.format(SPECIFIC_OLYMPICS_URL, selectedDate));
            wv.loadUrl(String.format(SPECIFIC_OLYMPICS_URL, selectedDate));
        }

        return view;
    }



}
