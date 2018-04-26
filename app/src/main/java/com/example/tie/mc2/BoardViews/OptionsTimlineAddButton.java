package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 24-Apr-18.
 */

public class OptionsTimlineAddButton extends android.support.v7.widget.AppCompatButton{
    final TimelineView targetView;
    public OptionsTimlineAddButton(Context context, final TimelineView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_add_timeline_button);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,15,0,15);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.addTimelineView();
            }
        });
    }

}
