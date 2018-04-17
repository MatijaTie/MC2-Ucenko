package com.example.tie.mc2.Gestures;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 15-Apr-18.
 */

public class myTextView extends android.support.v7.widget.AppCompatEditText {
    public myTextView(Context context) {
        super(context);
        setOnTouchListener(new ResizeListener(getContext(),this));
        this.setInputType(InputType.TYPE_NULL);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(), "klik", Toast.LENGTH_SHORT).show();
                return true;//true blokira da touch event ne ode do parenta
            }
        });
    }
}
