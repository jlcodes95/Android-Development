package com.example.webviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TAG="WEBVIEW-EXAMPLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLocalWebpagePressed(View view) {
        WebView webviewMain = (WebView) findViewById(R.id.webview);
        webviewMain.loadUrl("file:///android_asset/pancakes.html");
    }

    public void openURLPressed(View view) {
        // 1. Get the url from the user interface
        EditText etUrl = (EditText) findViewById(R.id.etUrl);
        String url = etUrl.getText().toString();
        Toast t = Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT);
        t.show();

        // 3. Open website in webview
        // --------------
        // Create a reference to the webview in the xml file
        WebView webviewMain = (WebView) findViewById(R.id.webview);

        // tells android to allow the webpage to run javascript
        webviewMain.getSettings().setJavaScriptEnabled(true);
        // load the webpage
        webviewMain.loadUrl(url);

        // tells android to show the webpage inside the app (do not open an external browser app)
        webviewMain.setWebViewClient(new WebViewClient());

        Log.d(TAG, "Finished loading webview");
    }
    public void openWebpagePressed(View view) {
        // 1. Get the url from the user interface
        EditText etUrl = (EditText) findViewById(R.id.etUrl);
        String url = etUrl.getText().toString();

        // 2. add an "http:" to the url
        String formattedUrl = "http://" + url;

        Toast t = Toast.makeText(getApplicationContext(), formattedUrl, Toast.LENGTH_SHORT);
        t.show();

        // 3. Open website in webview
        // --------------
        // Create a reference to the webview in the xml file

        //@
        Log.d(TAG, "Finished loading webview");
    }

    public void loadHTMLStringPressed(View view) {

        // 1 . write your custom html
        // ----------
        String customHtml = "<html><body><h1>Hello World!</h1></body></html>";
        WebView webviewMain = (WebView) findViewById(R.id.webview);
        webviewMain.loadData(customHtml, "text/html", "UTF-8");


    }


}
