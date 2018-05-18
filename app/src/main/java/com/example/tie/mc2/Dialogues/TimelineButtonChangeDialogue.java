package com.example.tie.mc2.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.BoardTimelineView;
import com.example.tie.mc2.BoardViews.BoardTimelineViewButton;
import com.example.tie.mc2.R;

import butterknife.OnTouch;

import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_CROWN;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_PEACE;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_SCROLL;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_WAR;

/**
 * Created by Tie on 18-May-18.
 */

public class TimelineButtonChangeDialogue extends Dialog implements OnTouchListener {
    private View peace, war, crown, scroll;
    private BoardTimelineViewButton targetView;
    private int icon;
    public TimelineButtonChangeDialogue(@NonNull Context context, BoardTimelineViewButton b) {
        super(context);
        targetView = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_timeline_button_change);

        peace = findViewById(R.id.dialogue_peace);
        war = findViewById(R.id.dialogue_war);
        crown = findViewById(R.id.dialogue_crown);
        scroll = findViewById(R.id.dialogue_scroll);
        peace.setOnTouchListener(this);
        war.setOnTouchListener(this);
        crown.setOnTouchListener(this);
        scroll.setOnTouchListener(this);


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.dialogue_peace:
                targetView.setIcon(TIMELINE_ICON_PEACE);
                dismiss();
                return true;
            case R.id.dialogue_war:
                targetView.setIcon(TIMELINE_ICON_WAR);
                dismiss();
                return true;
            case R.id.dialogue_crown:
                targetView.setIcon(TIMELINE_ICON_CROWN);
                dismiss();
                return true;
            case R.id.dialogue_scroll:
                targetView.setIcon(TIMELINE_ICON_SCROLL);
                dismiss();
                return true;
        }
        return false;
    }
}
