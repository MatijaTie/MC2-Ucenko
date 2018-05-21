package com.example.tie.mc2.BoardViews;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Rect;
import android.icu.text.UnicodeSetSpanner;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 31-Mar-18.
 */

public class RootView extends RelativeLayout implements View.OnTouchListener {
    private View mainView;

    private FrameLayout mainComponentHolder;
    private Button moveButton, resisizeButton;
    private boolean touchFlag;
    private float MIN_WIDTH, MIN_HEIGHT;
    private LinearLayout viewOptionsHolder;
    private FragmentBoard fragmentBoard;


    //minimalne dimenzije
    {
        MIN_WIDTH = 200;
        MIN_HEIGHT = 200;

    }
    public RootView(Context context) {
        super(context);

        //Inflate custom layout
        inflate(context, R.layout.board_component_holder, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(true);
            }
        });


        //inicijalizacija holdera za optionse viewa
        viewOptionsHolder = findViewById(R.id.view_options_holder);
        viewOptionsHolder.setVisibility(LinearLayout.VISIBLE);

        //initiate main component holder
        mainComponentHolder = findViewById(R.id.view_main_frame);
        mainComponentHolder.setFocusable(true);

        setOnTouchListener(this);

        //initiate move view
        moveButton = findViewById(R.id.view_drag_button);
        moveButton.bringToFront();
        moveButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        requestFocus();
                        animateView();
                        return true;
                }
                return true;
            }
        });

        resisizeButton = findViewById(R.id.view_resize_button);

        setFocusableInTouchMode(true);

    }

    public void hideOptionHolder(){
        viewOptionsHolder.setVisibility(INVISIBLE);
    }

    public void addViewToHolder(View v){
        mainComponentHolder.addView(v, ViewGroup.LayoutParams.MATCH_PARENT);
        mainView = v;
    }
    public void addViewToViewOptionsHolder(View v){
        viewOptionsHolder.addView(v);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.d("root view", "down");
                touchFlag = true;
                bringToFront();
                return true;

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
        return true;
    }

    public void setBorders(){
        FrameLayout layout = findViewById(R.id.view_main_frame);
        if(layout.getBackground() != null){
            layout.setBackground(null);
        }else{
            layout.setBackgroundResource(R.drawable.component_background);
        }
    }

    /*
    Intercepta touch event i na false prosljeduje childu
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestFocus();

        if (getViewTouchPosition(this, ev) == -1) {
            return false;
        }
        return true;
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
            return -1;
        } else if (dXv > (width / partNu) * (partNu - 1) && dYv > (height / partNu) * (partNu - 1)) {                              //DOLJE DESNO
            return 1;
        } else if (dXv > (width / partNu) * (partNu - 1) && dYv < (height / partNu) * (partNu - 1) && dYv > height / partNu) {       //SAMO DESNO
            return -1;
        } else {
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

    private void animateView(){
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                this);
        startDrag(data, shadowBuilder, this, 0);
        setVisibility(View.INVISIBLE);

    }

    public View getMainView() {
        return mainView;
    }


    public void showOptions(boolean b){
        if(b){
            viewOptionsHolder.setVisibility(VISIBLE);
            resisizeButton.setVisibility(VISIBLE);
        }else{
            viewOptionsHolder.setVisibility(INVISIBLE);
            resisizeButton.setVisibility(INVISIBLE);
        }
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused){
            showOptions(true);
        }else{
            showOptions(false);

        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

}


