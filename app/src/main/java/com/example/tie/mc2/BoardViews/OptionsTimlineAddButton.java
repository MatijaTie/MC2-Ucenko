package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 24-Apr-18.
 */

public class OptionsTimlineAddButton extends android.support.v7.widget.AppCompatButton{
    final BoardTimelineView targetView;

    public OptionsTimlineAddButton(Context context,final BoardTimelineView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_timeline_add_button);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.addTimelineView();
            }
        });
    }

}
