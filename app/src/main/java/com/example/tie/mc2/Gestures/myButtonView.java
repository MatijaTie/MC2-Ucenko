package com.example.tie.mc2.Gestures;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Tie on 15-Apr-18.
 */

public class myButtonView extends AppCompatButton  {
    public myButtonView(Context context) {
        super(context);
        setOnTouchListener(new ResizeListener(getContext(),this));
        this.setText("neki novi tekst");


    }

}
