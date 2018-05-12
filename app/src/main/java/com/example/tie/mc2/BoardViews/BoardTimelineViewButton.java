package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 22-Apr-18.
 */

public class BoardTimelineViewButton extends FrameLayout {
    Button timelineButton;
    EditText textPart, yearPart;
    private boolean isSmall;

    public BoardTimelineViewButton(Context context) {
        super(context);

        inflate(context, R.layout.component_timline_layout,  this);
        isSmall = false;
        timelineButton = findViewById(R.id.timelineBtn);
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
}
