package com.example.tourismapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.tourismapp.R;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        //setup toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.tbFAQ));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //load webview
        ((WebView) findViewById(R.id.wvFAQ)).loadUrl("file:///android_asset/faq.html");
    }
}
