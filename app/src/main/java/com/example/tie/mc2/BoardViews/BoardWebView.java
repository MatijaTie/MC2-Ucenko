package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tie.mc2.OptionButtons.OptionsWebviewBookmark;

import java.util.ArrayList;

/**
 * Created by Tie on 14-May-18.
 */

public class BoardWebView extends WebView{

    String startingWebsite = "https://www.google.com/";
    ArrayList<String> bookmarks = new ArrayList<>();
    RootView rootView;
    private int bookmarkCounter = 0;
    public BoardWebView(Context context, RootView rootView) {
        super(context);
        initWeb();
        this.rootView = rootView;
    }

    private void initWeb(){
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
        loadUrl(startingWebsite);
    }

    public void loadPage(){

    }

    public void goBackInHistory(){
        if(canGoBack()){
            goBack();
        }
    }

    public void addToBookmark(){
        if(bookmarks.contains(getUrl())){
            Toast.makeText(getContext(), "Bookmark već postoji", Toast.LENGTH_SHORT).show();
        }else{
            bookmarks.add(getUrl());
            rootView.addViewToViewOptionsHolder(new OptionsWebviewBookmark(getContext(), this, bookmarkCounter));
            bookmarkCounter++;
            Toast.makeText(getContext(),"Novi bookmark:\n"+getUrl(), Toast.LENGTH_SHORT).show();
        }

    }
    public void addToBookmark(String bookmark){
        if(!bookmarks.contains(bookmark)){
            bookmarks.add(bookmark);
            rootView.addViewToViewOptionsHolder(new OptionsWebviewBookmark(getContext(), this, bookmarkCounter));
            bookmarkCounter++;
        }

    }

    public void loadBookmark(int x){
        if(x < bookmarks.size() && !getUrl().equals(bookmarks.get(x))) {
            loadUrl(bookmarks.get(x));
        }
    }

    public ArrayList<String> getBookmarks(){
        return bookmarks;
    }

}
