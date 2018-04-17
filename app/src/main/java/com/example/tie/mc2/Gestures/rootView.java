package com.example.tie.mc2.Gestures;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 31-Mar-18.
 */

public class rootView extends RelativeLayout implements View.OnFocusChangeListener, View.OnTouchListener, GestureDetector.OnGestureListener {
    private GestureDetector gestureEvent;
    private RelativeLayout thisView;
    private FrameLayout mainComponentHolder;
    private Button moveButton;
    private float dX, dY;
    private boolean touchFlag;
    private float MIN_WIDTH, MIN_HEIGHT;
    private LinearLayout viewOptionsHolder;

    //minimalne dimenzije
    {
        MIN_WIDTH = 200;
        MIN_HEIGHT = 200;
    }

    public rootView(Context context, View v) {
        super(context);


        //Inflate custom layout
        inflate(context, R.layout.frame_layout_test, this);

        //inicijalizacija holdera za optionse viewa
        viewOptionsHolder = findViewById(R.id.view_options_holder);
        viewOptionsHolder.setVisibility(LinearLayout.VISIBLE);

        //initiate main component holder
        mainComponentHolder = findViewById(R.id.view_main_frame);
        mainComponentHolder.addView(v, ViewGroup.LayoutParams.MATCH_PARENT);
        mainComponentHolder.setFocusable(true);

        //reference to rootView class
        thisView = this;
        thisView.setOnTouchListener(this);
        thisView.setOnFocusChangeListener(this);

        //initiate move view
        moveButton = findViewById(R.id.view_drag_button);
        moveButton.bringToFront();
        moveButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return AnimationTouchListener(event);
            }
        });

        //initiate gesture event
        gestureEvent = new GestureDetector(context, this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = true;
                thisView.bringToFront();
                return gestureEvent.onTouchEvent(event);

            case MotionEvent.ACTION_MOVE:
                int sideClicked = getViewTouchPosition(v, event);
                if (sideClicked != -1) {
                    resizeView(v, event, sideClicked);
                }
                return true;

            case MotionEvent.ACTION_UP:
                touchFlag = false;
                return true;
        }
        return gestureEvent.onTouchEvent(event);
    }

    /*
    Intercepta touch event i na false prosljeduje childu
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getViewTouchPosition(this, ev) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int getViewTouchPosition(View v, MotionEvent event) {
        float dXv, dYv;
        float width, height;
        final float partNu = 10;

        width = v.getWidth();
        height = v.getHeight();

        dXv = event.getX();
        dYv = event.getY();

        if (dXv < width / partNu && dYv < height / partNu) {                                                              //GORE LIJEVO
            return -1;
        } else if (dXv > (width / partNu) * (partNu - 1) && dYv < height / partNu) { //                                        //GORE DESNO
            return -1;
        } else if (dXv < (width / partNu) * (partNu - 1) && dXv > width / partNu && dYv < height / partNu) {                     //SAMO GORE
            return -1;
        } else if (dXv < width / partNu && dYv > (height / partNu) * (partNu - 1)) {                                           //DOLJE LIJEVO
            return 1;
        } else if (dXv < width / partNu && dYv > height / partNu && dYv < (height / partNu) * (partNu - 1)) {                  //SAMO LIJEVO
            return -1;
        } else if (dXv > width / partNu && dXv < (width / partNu) * (partNu - 1) && dYv > (height / partNu) * (partNu - 1)) {        //SAMO DOLJE
            return 2;
        } else if (dXv > (width / partNu) * (partNu - 1) && dYv > (height / partNu) * (partNu - 1)) {                              //DOLJE DESNO
            return 1;
        } else if (dXv > (width / partNu) * (partNu - 1) && dYv < (height / partNu) * (partNu - 1) && dYv > height / partNu) {       //SAMO DESNO
            return 3;
        } else {
            Log.d("touch pos", "nesto trece");
            return -1;
        }
    }

    private void resizeView(View view, MotionEvent event, int resizeDirection) {
        float eventX, eventY;
        ViewGroup.LayoutParams params;
        eventX = event.getX();
        eventY = event.getY();
        params = view.getLayoutParams();

        if (eventX > MIN_WIDTH && eventY > MIN_HEIGHT) {
            switch (resizeDirection) {
                case 1:
                    params.width = (int) (eventX);
                    params.height = (int) (eventY);
                    break;
                case 2:
                    params.height = (int) (eventY);
                    break;
                case 3:
                    params.width = (int) (eventX);
                    break;
            }
            view.setLayoutParams(params);
        }
    }

    public boolean AnimationTouchListener(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setCordinates(thisView, event);
                break;

            case MotionEvent.ACTION_MOVE:
                animateView(event);
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // Log.d("gesture", "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //  Log.d("gesture","OnSingle Tap Up");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }


    @Override
    public void onLongPress(MotionEvent e) {
        //     Log.d("gesture","longPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    private void setCordinates(View v, MotionEvent event) {
        dX = v.getX() - event.getRawX();
        dY = v.getY() - event.getRawY();
    }

    private void animateView(MotionEvent event) {
        thisView.animate()
                .x(event.getRawX() + dX)
                .y(event.getRawY() + dY)
                .setDuration(0)
                .start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d("hasfocus","trigger");
        if (hasFocus) {
            Toast.makeText(getContext(), "has focus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "nema focus", Toast.LENGTH_SHORT).show();
        }
    }
}


