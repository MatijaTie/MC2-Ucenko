package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
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
    RelativeLayout.LayoutParams params;
    String text;                        //kod skupljanja podataka text = this.getText()
    int textSize, backgroundColor, textColor;

    public BoardTextView(Context context) {
        super(context);
        setTypeface(Typeface.SERIF);
        setGravity(Gravity.START);

        textColor = getCurrentTextColor();
        textSize = (int) pixelsToSp(getContext(),getTextSize());

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


    public int getCustomTextSize() {
        return textSize;
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

    public int getBackgroundColor() {

        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }
}

