package com.allnone.app.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.allnone.app.allnone.R;


public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String title = (String) getIntent().getExtras().get("title");
        String content = (String) getIntent().getExtras().get("summary");


        Log.d("DEBUG", "title:\t" + title);

        Log.d("DEBUG", "summary:\t\t" + content);


        WebView webView = (WebView) findViewById(R.id.detailsWebView);


        webView.loadData(content, "application/atom+xml", "UTF-8");

    }
}
