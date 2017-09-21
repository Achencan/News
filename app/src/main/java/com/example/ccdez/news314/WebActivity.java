package com.example.ccdez.news314;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 功能：接收Intent传过来的url，打开新闻信息
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

        //接收url
        url = intent.getStringExtra("url");

        setWebView(url);
    }

    public void setWebView(String url) {

        //js支持
//        webView.getSettings().setJavaScriptEnabled(true);
        //新窗口不打开内置浏览器
        webView.setWebViewClient(new WebViewClient());
        //加载
        webView.loadUrl(url);

    }
}
