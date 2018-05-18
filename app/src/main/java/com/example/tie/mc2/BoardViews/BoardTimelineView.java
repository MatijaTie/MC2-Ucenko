package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

import java.util.ArrayList;

import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_PEACE;

/**
 * Created by Tie on 21-Apr-18.
 */

public class BoardTimelineView extends LinearLayout {
    RelativeLayout mainLayout;
    LinearLayout childLayout;
    ArrayList<BoardTimelineViewButton> childHolder;

    public BoardTimelineView(final Context context, ViewGroup parent) {
        super(context);
        mainLayout = (RelativeLayout) inflate(context, R.layout.component_timeline, parent);
        childLayout = mainLayout.findViewById(R.id.toolbar_child_holder);
        childHolder = new ArrayList<>();



    }

    public BoardTimelineView getTimelineView(){
        return this;
    }

    public void addTimelineView(){
        BoardTimelineViewButton newTimlineButton = new BoardTimelineViewButton(getContext(), this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
       // params.gravity = Gravity.CENTER;
        params.setMargins(10,10,10,10);
        newTimlineButton.setLayoutParams(params);
        childLayout.addView(newTimlineButton);
        childHolder.add(newTimlineButton);
        bringChildToFront(newTimlineButton);

    }
    public void addTimelineView(String yearPart, String textPart, int marginLeft, int icon){
        BoardTimelineViewButton newTimlineButton = new BoardTimelineViewButton(getContext(), this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(marginLeft,10,10,10);
        newTimlineButton.setLayoutParams(params);
        newTimlineButton.setTextPart(textPart);
        newTimlineButton.setYearPart(yearPart);
        newTimlineButton.setIcon(icon);
        childLayout.addView(newTimlineButton);
        childHolder.add(newTimlineButton);
        bringChildToFront(newTimlineButton);
    }

    public void stabilizeDrag(BoardTimelineViewButton view, int newMargin){
        for(int i = 0; i < childHolder.size(); i++){
            if(childHolder.get(i).equals(view)){
                if(i+1 < childHolder.size()){
                    childHolder.get(i+1).setDragMargin(newMargin);
                }
            }
        }
    }

    public ArrayList<BoardTimelineViewButton> getChildHolder(){
        return childHolder;
    }

    public void removeTimelineView(){
        if(childHolder.size() > 0){
            childHolder.get(childHolder.size()-1).setVisibility(GONE);
            childHolder.remove(childHolder.size()-1);
        }

    }
}
