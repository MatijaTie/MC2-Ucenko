package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.BoardViews.BoardWebView;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 15-May-18.
 */

public class OptionsWebviewBackButton extends android.support.v7.widget.AppCompatButton {
    final BoardWebView targetView;

    public OptionsWebviewBackButton(Context context, final BoardWebView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_webview_back);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0, 0, 0, 10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.goBackInHistory();
            }
        });
    }
}
