package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tie.mc2.Dialogues.TextChangeDialogue;

/**
 * Created by Tie on 26-Apr-18.
 */

public class BoardTextView extends android.support.v7.widget.AppCompatEditText {
    RelativeLayout.LayoutParams params;
    String text;                        //kod skupljanja podataka text = this.getText()

    public BoardTextView(Context context) {
        super(context);
        setTypeface(Typeface.SERIF);
        setGravity(Gravity.START);
    }

    public void callTextDialogue() {
        TextChangeDialogue dialogue = new TextChangeDialogue(getContext());
        dialogue.show();
    }
}

