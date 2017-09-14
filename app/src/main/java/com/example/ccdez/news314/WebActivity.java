package com.example.ccdez.news314;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ccdez on 2017/9/13 0013.
 */

public class WebActivity extends Activity {

    private WebView webView;
    private String url;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        webView = (WebView) findViewById(R.id.news_data);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        setWebView(url);
    }

    public void setWebView(String url) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}
