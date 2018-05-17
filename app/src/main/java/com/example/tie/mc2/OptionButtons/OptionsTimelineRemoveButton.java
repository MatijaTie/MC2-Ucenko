package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.BoardTimelineView;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 17-May-18.
 */

public class OptionsTimelineRemoveButton extends android.support.v7.widget.AppCompatButton{
    final BoardTimelineView targetView;

    public OptionsTimelineRemoveButton(Context context, final BoardTimelineView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_timeline_remove);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.removeTimelineView();
            }
        });
    }

}
