package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 22-Apr-18.
 */

public class TimelineViewButton extends FrameLayout {
    public TimelineViewButton(Context context) {
        super(context);
        inflate(context, R.layout.proba, (ViewGroup) this);
       // setBackgroundResource(R.drawable.component_timline_timestamp);
    }

    public TimelineViewButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.proba, (ViewGroup) this);
    }

    public TimelineViewButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.proba, this);
    }

    public void bringThisToFront(){
        bringToFront();
    }
}
