package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tie on 26-Apr-18.
 */

public class BoardTextView extends android.support.v7.widget.AppCompatEditText {
    RelativeLayout.LayoutParams params;
    public BoardTextView(Context context) {
        super(context);
        setGravity(Gravity.START);
    }
}
