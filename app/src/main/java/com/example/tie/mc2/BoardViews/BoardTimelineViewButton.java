package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 22-Apr-18.
 */

public class BoardTimelineViewButton extends FrameLayout {
    Button timelineButton;
    private boolean isSmall;

    public BoardTimelineViewButton(Context context) {
        super(context);

        inflate(context, R.layout.component_timline_layout,  this);
        isSmall = false;
        timelineButton = findViewById(R.id.timelineBtn);
        bringToFront();
        bringThisToFront();

    }



    public void bringThisToFront(){
        bringToFront();
    }
}
