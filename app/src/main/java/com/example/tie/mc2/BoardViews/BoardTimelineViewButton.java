package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.Dialogues.ColorChangeDialogue;
import com.example.tie.mc2.Dialogues.TimelineButtonChangeDialogue;
import com.example.tie.mc2.R;

import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_CROWN;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_PEACE;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_SCROLL;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_WAR;

/**
 * Created by Tie on 22-Apr-18.
 */

public class BoardTimelineViewButton extends FrameLayout implements View.OnTouchListener{
    private FrameLayout timelineButton;
    private EditText textPart, yearPart;
    private boolean flag;
    int x, oldX = 0, time = 0;
    BoardTimelineView parent;
    private LinearLayout.LayoutParams params;
    private View img;
    private int icon = 0;


    public BoardTimelineViewButton(Context context, BoardTimelineView parent) {
        super(context);

        final BoardTimelineViewButton ba = (BoardTimelineViewButton) inflate(context, R.layout.component_timline_layout,  this);
        this.parent = parent;

        flag = true;
        img = findViewById(R.id.timeline_icon_holder);
        timelineButton = findViewById(R.id.timelineBtn);
        timelineButton.setOnTouchListener(this);
        timelineButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(flag){
                    TimelineButtonChangeDialogue dialogue = new TimelineButtonChangeDialogue(getContext(), ba);
                    dialogue.show();
                    flag = false;
                }
                return true;
            }
        });

        textPart = findViewById(R.id.board_timeline_part_text);
        yearPart = findViewById(R.id.board_timeline_part_year);

        bringToFront();
    }

    public void setIcon(int indentifier){
        icon = indentifier;
        switch (icon){
            case TIMELINE_ICON_WAR:
                img.setBackgroundResource(R.drawable.ic_timeline_buttone_fight);
                break;

            case TIMELINE_ICON_PEACE:
                img.setBackgroundResource(R.drawable.ic_timeline_buttone_peace);
                break;

            case TIMELINE_ICON_CROWN:
                img.setBackgroundResource(R.drawable.ic_timeline_buttone_crown);
                break;

            case TIMELINE_ICON_SCROLL:
                img.setBackgroundResource(R.drawable.ic_timeline_button_scroll);
                break;

        }
    }

    public int getIcon(){
        return icon;
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                flag = true;
                if(params == null){
                    params = (LinearLayout.LayoutParams) getLayoutParams();
                }
                x = (int) v.getX();

                return v.onTouchEvent(event);

            case MotionEvent.ACTION_MOVE:
                time++;
                int newX = (int) event.getX();
                if(Math.abs(newX) > 5 && Math.abs(newX-oldX) > 5 && time > 5){
                    Log.d("on move","true");
                    flag = false;
                    params.leftMargin += newX;
                    parent.stabilizeDrag(this, newX);
                    setLayoutParams(params);

                }
                oldX = newX;
                return false;

            case MotionEvent.ACTION_UP:
                flag = true;
                time = 0;
                return true;
        }
        return false;
    }

    public void setDragMargin(int newX){
        params = (LinearLayout.LayoutParams) getLayoutParams();
        params.leftMargin -= newX;
    }

    public int getLeftMargin(){
        params = (LinearLayout.LayoutParams) getLayoutParams();
        return params.leftMargin;
    }
    public void setLeftMargin(int margin){
        params = (LinearLayout.LayoutParams) getLayoutParams();
        params.leftMargin = margin;
    }
}
