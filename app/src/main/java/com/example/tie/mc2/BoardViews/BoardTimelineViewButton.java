package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 22-Apr-18.
 */

public class BoardTimelineViewButton extends FrameLayout implements View.OnTouchListener{
    private Button timelineButton;
    private EditText textPart, yearPart;
    int x;
    BoardTimelineView parent;
    private int location;
    private BoardTimelineViewButton thisView;
    private LinearLayout.LayoutParams params;


    public BoardTimelineViewButton(Context context, BoardTimelineView parent) {
        super(context);

        inflate(context, R.layout.component_timline_layout,  this);
        this.bringToFront();
        this.parent = parent;
        thisView = this;
        timelineButton = findViewById(R.id.timelineBtn);
        timelineButton.setOnTouchListener(this);
        bringToFront();
        bringThisToFront();

        textPart = findViewById(R.id.board_timeline_part_text);
        yearPart = findViewById(R.id.board_timeline_part_year);
    }

    public String getTextPart(){
        return textPart.getText().toString();
    }

    public String getYearPart(){
        return yearPart.getText().toString();
    }

    public void setYearPart(String year){
        yearPart.setText(year);
    }

    public void setTextPart(String text){
        textPart.setText(text);
    }


    public void bringThisToFront(){
        bringToFront();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(params == null){
                    params = (LinearLayout.LayoutParams) getLayoutParams();
                }
                x = (int) v.getX();
                return true;

            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getX();
                if(Math.abs(x - newX) > 5){
                    params.leftMargin += newX;
                    parent.stabilizeDrag(thisView, newX);
                    setLayoutParams(params);
                    }
                return true;
        }
        return false;
    }

    public void setDragMargin(int newX){
        params = (LinearLayout.LayoutParams) getLayoutParams();
        params.leftMargin -= newX;
    }
}
