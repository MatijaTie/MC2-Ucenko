package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.BoardViews.BoardWebView;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 15-May-18.
 */

public class OptionsWebviewBookmark extends android.support.v7.widget.AppCompatButton {
    private BoardWebView targetView;
    final int bookmarkNumber;

    public OptionsWebviewBookmark(Context context, final BoardWebView targetView, final int bookmarkNumber) {
        super(context);
        this.targetView = targetView;
        this.bookmarkNumber = bookmarkNumber;
        setBackgroundResource(R.drawable.options_webview_new_bookmark);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(75, 100);
        params.setMargins(0, 0, 0, 10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.loadBookmark(bookmarkNumber);
            }
        });
    }
}