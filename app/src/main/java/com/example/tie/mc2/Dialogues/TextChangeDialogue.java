package com.example.tie.mc2.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 28-Apr-18.
 */

public class TextChangeDialogue extends Dialog {
    public TextChangeDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_text_modifier);
        super.onCreate(savedInstanceState);
    }
}
