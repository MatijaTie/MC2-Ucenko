package com.example.tie.mc2.Listeners;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tie on 24-Mar-18.
 */

public class ToolbarIconsTouchListener implements View.OnTouchListener{
    String viewType;

    public ToolbarIconsTouchListener(String viewType){
        this.viewType = viewType;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ClipData viewShadow = ClipData.newPlainText(viewType, view.getWidth()+":"+view.getHeight());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(viewShadow, shadowBuilder, view, 0);
                return true;
        }
        return false;
    }
}
