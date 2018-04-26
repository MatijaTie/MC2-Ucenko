package com.example.tie.mc2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tie.mc2.BoardViews.TimelineView;
import com.example.tie.mc2.BoardViews.pictureView;
import com.example.tie.mc2.BoardViews.OptionsTimlineAddButton;
import com.example.tie.mc2.BoardViews.rootView;
import com.example.tie.mc2.Listeners.TrashOnDragListener;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 10-Mar-18.
 */

public class FragmentBoard extends Fragment implements View.OnDragListener, View.OnTouchListener{

    public final String INDENTIFIER_BUTTON = "class com.example.tie.mc2.ToolbarComponents.TimelineComponentDraggable";
    public final String INDENTIFIER_BUTTON2 = "class com.example.tie.mc2.ToolbarComponents.TextComponentDraggable";
    public final String INDENTIFIER_BUTTON3 = "class com.example.tie.mc2.ToolbarComponents.ImageComponentDraggable";


    private RelativeLayout mainLayout;
    private FrameLayout trash;
    private Context context;
    Point point;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RelativeLayout mainLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_ploca, container, false);
        mainLayout.setOnDragListener(this);
        mainLayout.setOnTouchListener(this);
        context = getContext();
        this.mainLayout = mainLayout;
        return mainLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        trash = view.findViewById(R.id.trash);
        trash.bringToFront();
        trash.setOnDragListener(new TrashOnDragListener(trash));
        super.onViewCreated(view, savedInstanceState);
    }

    public static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);
        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }

    //metoda koja ce primati listenere za drag evente
    @Override
    public boolean onDrag(View boardView, DragEvent event) {
        //pozicija gdje se dropa
        String sizeString, typeString;
        View draggedView;

        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_LOCATION:
                point = getTouchPositionFromDragEvent(boardView, event);
                return true;

            case DragEvent.ACTION_DROP:
                if (event.getClipData().getItemAt(0).getText().length() > 0){
                    sizeString = event.getClipData().getItemAt(0).getText().toString();
                    typeString = event.getClipData().getDescription().getLabel().toString();
                    createDraggedView(event, sizeString, typeString);
                }
                else{                                                                               //Mozda posaviti na else if sa if this == view.getparent()
                    draggedView = (View) event.getLocalState();
                    setDraggedViewLocation(draggedView, point);
                }

                return true;
        }
        return true;
    }

    private void createDraggedView(DragEvent event, String sizeString, String typeString){
        float posX, posY;
        float offsetX,offsetY;
        ViewGroup.LayoutParams params;
        ViewGroup viewGroup;

        posX = event.getX();
        posY = event.getY();
        offsetX = Float.valueOf(sizeString.substring(0, sizeString.indexOf(":")))/2;
        offsetY = Float.valueOf(sizeString.substring(sizeString.indexOf(":")+1))/2;

        switch (typeString){
            case INDENTIFIER_BUTTON:
                //ovdje idu konstruktori i dodavanje custom viewa na plocu
                //primjer:

                rootView rootView = new rootView(context);
                TimelineView timelineView = new TimelineView(context, rootView);
                OptionsTimlineAddButton OptionsTimlineAddButton = new OptionsTimlineAddButton(context, timelineView);
                rootView.addViewToHolder(timelineView);
                rootView.addViewToViewOptionsHolder(OptionsTimlineAddButton);
                mainLayout.addView(rootView);
                params = rootView.getLayoutParams();
                params.width = 750;
                params.height = 350;
                rootView.setLayoutParams(params);
                rootView.setX(posX-offsetX);
                rootView.setY(posY-offsetY);
                break;

            case INDENTIFIER_BUTTON2:
                viewGroup = new rootView(context,new EditText(context));
                mainLayout.addView(viewGroup);
                params = viewGroup.getLayoutParams();
                params.width = 400;
                params.height = 400;
                viewGroup.setLayoutParams(params);
                viewGroup.setX(posX-offsetX);
                viewGroup.setY(posY-offsetY);
                break;

            case INDENTIFIER_BUTTON3:
                viewGroup = new rootView(context,new pictureView(context));
                mainLayout.addView(viewGroup);
                params = viewGroup.getLayoutParams();
                params.width = 400;
                params.height = 400;
                viewGroup.setLayoutParams(params);
                viewGroup.setX(posX-offsetX);
                viewGroup.setY(posY-offsetY);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                //Toast.makeText(getContext(), "action up", Toast.LENGTH_SHORT).show();
                return true;

            case MotionEvent.ACTION_DOWN:
                removeSoftKeyboard(v);
                return true;

        }
        return false;
    }

    public void removeSoftKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void setDraggedViewLocation(View draggedView, Point point) {
        int newPosX, newPosY;
        newPosX = point.x - mainLayout.getWidth() / 15 - draggedView.getWidth() / 2;
        newPosY = point.y - draggedView.getHeight() / 2 - 45;

        if(newPosX < 0) {
            draggedView.setX(0);
        }else {
            draggedView.setX(newPosX);
        }

        if(newPosY < 0){
            draggedView.setY(0);
        }else{
            draggedView.setY(newPosY);
        }
        draggedView.setVisibility(View.VISIBLE);
    }


}
