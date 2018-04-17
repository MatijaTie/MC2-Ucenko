package com.example.tie.mc2.Gestures;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Tie on 15-Apr-18.
 */

public class ResizeListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private GestureDetector gestureEvent;
    private float dX, dY;
    private View rootView;
    private boolean touchFlag;
    private float MIN_WIDTH, MIN_HEIGHT;
    {
        MIN_WIDTH = 200;
        MIN_HEIGHT = 200;
    }

    public ResizeListener(Context context, View root) {
        gestureEvent = new GestureDetector(context, this);
        rootView = root;
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                if(!touchFlag){
                    setCordinates(event);
                }
                touchFlag = true;
                return gestureEvent.onTouchEvent(event);

            case MotionEvent.ACTION_MOVE:
                int sideClicked = getViewTouchPosition(v,event);
                if(sideClicked == -1){
                    animateView(rootView, event);
                }else{
                    resizeView(v, event, sideClicked);
                }
                return true;

            case MotionEvent.ACTION_UP:
                touchFlag = false;
                return true;
        }
        return gestureEvent.onTouchEvent(event);
    }

    private void setCordinates(MotionEvent event){
        dX = rootView.getX() - event.getRawX();
        dY = rootView.getY() - event.getRawY();
    }

    private void animateView(View v, MotionEvent event){
        v.animate()
                .x(event.getRawX() + dX)
                .y(event.getRawY() + dY)
                .setDuration(0)
                .start();
    }

    public int getViewTouchPosition(View v, MotionEvent event){
        float dXv, dYv;
        float width, height;
        final float partNu = 10;

        width = v.getWidth();
        height = v.getHeight();

        dXv = event.getX();
        dYv = event.getY();

        if(dXv < width/partNu && dYv < height/partNu){                                                              //GORE LIJEVO
            return -1;
        }else if(dXv > (width/partNu)*(partNu-1) && dYv < height/partNu){ //                                        //GORE DESNO
            return -1;
        }else if(dXv < (width/partNu)*(partNu-1) && dXv > width/partNu && dYv < height/partNu){                     //SAMO GORE
            return -1;
        }else if(dXv < width/partNu && dYv > (height/partNu)*(partNu-1)){                                           //DOLJE LIJEVO
            return 1;
        }else if(dXv < width/partNu &&  dYv > height/partNu && dYv < (height/partNu)*(partNu-1) ){                  //SAMO LIJEVO
            return -1;
        }else if(dXv > width/partNu && dXv < (width/partNu)*(partNu-1) && dYv > (height/partNu)*(partNu-1)){        //SAMO DOLJE
            return 2;
        }else if(dXv > (width/partNu)*(partNu-1) && dYv > (height/partNu)*(partNu-1)){                              //DOLJE DESNO
            return 1;
        }else if(dXv > (width/partNu)*(partNu-1) && dYv < (height/partNu)*(partNu-1) && dYv > height/partNu){       //SAMO DESNO
            return 3;
        }else {
            Log.d("touch pos","nesto trece");
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


    public void toastView(){
        Toast.makeText(rootView.getContext(), "abstraktni", Toast.LENGTH_SHORT).show();
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
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {
        //     Log.d("gesture","longPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}

