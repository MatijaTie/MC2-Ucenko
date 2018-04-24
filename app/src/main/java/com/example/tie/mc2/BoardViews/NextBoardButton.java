package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tie on 21-Apr-18.
 */

public class NextBoardButton extends AppCompatButton implements View.OnTouchListener {
    public NextBoardButton(Context context) {
        super(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
