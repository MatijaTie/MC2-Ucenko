package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 21-Apr-18.
 */

public class BoardTimelineView extends LinearLayout {
    BoardTimelineView thisView;


    public BoardTimelineView(final Context context, ViewGroup parent) {
        super(context);
        inflate(context, R.layout.component_timeline, parent);

        //setBackgroundResource(R.drawable.component_timeline_line);
        thisView = this;


    }

    public BoardTimelineView getTimelineView(){
        return this;
    }

    public void addTimelineView(){
        BoardTimelineViewButton newTimlineButton = new BoardTimelineViewButton(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(10,10,10,10);
        newTimlineButton.setLayoutParams(params);

        thisView.addView(newTimlineButton);
        thisView.bringChildToFront(newTimlineButton);

    }


}