package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

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

public class BoardMapDrawingLayout extends BoardDrawingLayout implements View.OnTouchListener {
    private boolean isPainting;
    private int map;

    public BoardMapDrawingLayout(Context context) {
        super(context);
        setPainting(false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public int getDrawingColor() {
        return super.getDrawingColor();
    }

    @Override
    public void setDrawingColor(int color) {
        super.setDrawingColor(color);
    }

    @Override
    public void setPainting(Boolean b) {
        super.setPainting(b);
    }

    @Override
    public void setErasing(Boolean b) {
        super.setErasing(b);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



    public void setMap(int indentifier){
        map = indentifier;
        switch (map){
            case MAP_CROATIA:
                setBackgroundResource(R.drawable.map_croatia);
                break;

            case MAP_EUROPE:
                setBackgroundResource(R.drawable.map_europe);
                break;

            case MAP_WORLD:
                setBackgroundResource(R.drawable.map_world);
                break;

        }
    }

    public int getMap(){
        return map;
    }

}
