package com.example.tie.mc2.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import com.example.tie.mc2.BoardViews.BoardMapDrawingLayout;
import com.example.tie.mc2.R;

import static com.example.tie.mc2.StaticValues.DataKeys.MAP_CROATIA;
import static com.example.tie.mc2.StaticValues.DataKeys.MAP_EUROPE;
import static com.example.tie.mc2.StaticValues.DataKeys.MAP_WORLD;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_CROWN;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_PEACE;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_SCROLL;
import static com.example.tie.mc2.StaticValues.DataKeys.TIMELINE_ICON_WAR;

/**
 * Created by Tie on 19-May-18.
 */

public class MapChangeDialogue extends Dialog implements View.OnTouchListener {
    BoardMapDrawingLayout targetView;
    private View croatia, europe, world;

    public MapChangeDialogue(@NonNull Context context, BoardMapDrawingLayout targetView) {
        super(context);
        this.targetView = targetView;
        setContentView(R.layout.dialogue_map_change);
        croatia = findViewById(R.id.map_view_croatia);
        europe = findViewById(R.id.map_view_europe);
        world = findViewById(R.id.map_view_world);
        croatia.setOnTouchListener(this);
        europe.setOnTouchListener(this);
        world.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.map_view_croatia:
                targetView.setMap(MAP_CROATIA);
                dismiss();
                return true;
            case R.id.map_view_europe:
                targetView.setMap(MAP_EUROPE);
                dismiss();
                return true;
            case R.id.map_view_world:
                targetView.setMap(MAP_WORLD);
                dismiss();
                return true;

        }
        return false;
    }
}
