package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tie.mc2.R;

import java.util.jar.Attributes;

/**
 * Created by Tie on 21-Apr-18.
 */

public class TimelineView extends LinearLayout {
    TimelineView thisView;


    public TimelineView(final Context context, ViewGroup parent) {
        super(context);
        inflate(context, R.layout.component_timeline, parent);

        //setBackgroundResource(R.drawable.component_timeline_line);
        thisView = this;


    }

    public TimelineView getTimelineView(){
        return this;
    }

    public void addTimelineView(){
        TimelineViewButton newTimlineButton = new TimelineViewButton(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(10,10,10,10);
        newTimlineButton.setLayoutParams(params);

        thisView.addView(newTimlineButton);
        thisView.bringChildToFront(newTimlineButton);

    }


}
