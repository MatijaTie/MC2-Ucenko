package com.example.tie.mc2.Fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tie.mc2.Gestures.myButtonView;
import com.example.tie.mc2.Listeners.ToolbarIconsTouchListener;
import com.example.tie.mc2.R;
import com.example.tie.mc2.ToolbarComponents.ImageComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.TextComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.TimelineComponentDraggable;

/**
 * Created by Tie on 12-Mar-18.
 */

public class FragmentToolbar extends Fragment {
    Button testBtn;
    ImageButton testBtn2;
    ImageView testBtn3;
    LinearLayout toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_toolbar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.layout_toolbar_main);

        TimelineComponentDraggable timelineComponentDraggable = new TimelineComponentDraggable(getContext());
        timelineComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(timelineComponentDraggable.getClass().toString()));

        TextComponentDraggable textComponentDraggable = new TextComponentDraggable(getContext());
        textComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(textComponentDraggable.getClass().toString()));

        ImageComponentDraggable imageComponentDraggable = new ImageComponentDraggable(getContext());
        imageComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(imageComponentDraggable.getClass().toString()));


        toolbar.addView(timelineComponentDraggable);
        toolbar.addView(textComponentDraggable);
        toolbar.addView(imageComponentDraggable);
    }
}
