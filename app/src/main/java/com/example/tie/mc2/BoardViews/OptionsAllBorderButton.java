package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 28-Apr-18.
 */

public class OptionsAllBorderButton extends AppCompatButton {
    final RootView targetView;

    public OptionsAllBorderButton(Context context, final RootView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_change_border);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.setBorders();
            }
        });
    }

}