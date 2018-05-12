package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tie.mc2.Dialogues.TextChangeDialogue;

/**
 * Created by Tie on 26-Apr-18.
 */

public class BoardTextView extends android.support.v7.widget.AppCompatEditText {
    int textSize, backgroundColor, textColor;

    public BoardTextView(Context context) {
        super(context);
        setTypeface(Typeface.SERIF);
        setGravity(Gravity.START);
        setPadding(0,0,0,0);

        Drawable background = this.getBackground();
        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();
            backgroundColor = color;
        }

    }

    public void callTextDialogue() {
        TextChangeDialogue dialogue = new TextChangeDialogue(getContext(), this);
        dialogue.show();
    }

    private float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    public float getCustomTextSize() {
        return pixelsToSp(getContext(), getTextSize());
    }

    public void setCustomTextSize(int textSize) {
        this.textSize = textSize;
    }


    public void setCustomBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setCustomTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getEnteredBackgroundColor() {
        Drawable background = this.getBackground();
        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();
            return color;
        }
        return Color.TRANSPARENT;
    }

}

