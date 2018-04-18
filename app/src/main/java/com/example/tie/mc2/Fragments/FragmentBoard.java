package com.example.tie.mc2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.Gestures.myButtonView;
import com.example.tie.mc2.BoardViews.rootView;
import com.example.tie.mc2.Listeners.TrashOnDragListener;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 10-Mar-18.
 */

public class FragmentBoard extends Fragment implements View.OnDragListener{

    public static final String INDENTIFIER_BUTTON = "class android.support.v7.widget.AppCompatButton";
    public static final String INDENTIFIER_BUTTON2 = "class android.support.v7.widget.AppCompatImageButton";

    private RelativeLayout mainLayout;
    private FrameLayout trash;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RelativeLayout mainLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_ploca, container, false);
        mainLayout.setOnDragListener(this);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });



        context = getContext();
        this.mainLayout = mainLayout;

        return mainLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //view.setOnDragListener(this);
        trash = view.findViewById(R.id.trash);
        trash.bringToFront();
        trash.setOnDragListener(new TrashOnDragListener(trash));
        super.onViewCreated(view, savedInstanceState);
    }

    //metoda koja ce primati listenere za drag evente
    @Override
    public boolean onDrag(View boardView, DragEvent event) {
        //pozicija gdje se dropa
        String sizeString, typeString;

        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                return true;

            case DragEvent.ACTION_DROP:
                if (event.getClipData().getItemAt(0).getText().length() > 0){
                    sizeString = event.getClipData().getItemAt(0).getText().toString();
                    typeString = event.getClipData().getDescription().getLabel().toString();
                    createDraggedView(event, sizeString, typeString);
                }

                return true;
        }
        return false;
    }

    private void createDraggedView(DragEvent event, String sizeString, String typeString){
        float posX, posY;
        float offsetX,offsetY;
        ViewGroup.LayoutParams params;
        View view;

        posX = event.getX();
        posY = event.getY();
        offsetX = Float.valueOf(sizeString.substring(0, sizeString.indexOf(":")))/2;
        offsetY = Float.valueOf(sizeString.substring(sizeString.indexOf(":")+1))/2;

        switch (typeString){
            case INDENTIFIER_BUTTON:
                //ovdje idu konstruktori i dodavanje custom viewa na plocu
                //primjer:

                view = new myButtonView(context);
                mainLayout.addView(view);
                params = view.getLayoutParams();
                params.width = 400;
                params.height = 400;
                view.setLayoutParams(params);
                view.setX(posX-offsetX);
                view.setY(posY-offsetY);
                break;

            case INDENTIFIER_BUTTON2:
                /*
                //ovdje idu konstruktori i dodavanje custom viewa na plocu
                //primjer:
                view = new myTextView(context);
                mainLayout.addView(view);
                params = view.getLayoutParams();
                params.width = 400;
                params.height = 400;
                view.setLayoutParams(params);
                view.setX(posX-offsetX);
                view.setY(posY-offsetY);
                break;
                */
                ViewGroup viewGroup = new rootView(context,new EditText(context));
                mainLayout.addView(viewGroup);
                params = viewGroup.getLayoutParams();
                params.width = 400;
                params.height = 400;
                viewGroup.setLayoutParams(params);
                viewGroup.setX(posX-offsetX);
                viewGroup.setY(posY-offsetY);
        }
    }


}
