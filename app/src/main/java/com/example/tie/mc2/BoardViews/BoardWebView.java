package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Tie on 14-May-18.
 */

public class BoardWebView extends WebView{
    String startingWebsite = "https://www.google.com/";
    public BoardWebView(Context context) {
        super(context);
        initWeb();
    }

    private void initWeb(){
        WebSettings settings = getSettings();
        //settings.setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
        loadUrl(startingWebsite);

    }

    public void goBackInHistory(){
        if(canGoBack()){
            goBack();
        }
    }
}
