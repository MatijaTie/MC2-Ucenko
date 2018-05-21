package com.example.tie.mc2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.tie.mc2.Listeners.ToolbarIconsTouchListener;
import com.example.tie.mc2.R;
import com.example.tie.mc2.ToolbarComponents.ImageComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.MapComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.TextComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.TimelineComponentDraggable;
import com.example.tie.mc2.ToolbarComponents.WebviewComponentDraggable;

/**
 * Created by Tie on 12-Mar-18.
 */

public class FragmentToolbar extends android.support.v4.app.Fragment {
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
        setToolbar();
    }

    private void setToolbar(){
        TimelineComponentDraggable timelineComponentDraggable = new TimelineComponentDraggable(getContext());
        timelineComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(timelineComponentDraggable.getClass().toString()));

        TextComponentDraggable textComponentDraggable = new TextComponentDraggable(getContext());
        textComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(textComponentDraggable.getClass().toString()));

        ImageComponentDraggable imageComponentDraggable = new ImageComponentDraggable(getContext());
        imageComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(imageComponentDraggable.getClass().toString()));

        WebviewComponentDraggable webviewComponentDraggable = new WebviewComponentDraggable(getContext());
        webviewComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(webviewComponentDraggable.getClass().toString()));

        MapComponentDraggable mapComponentDraggable = new MapComponentDraggable(getContext());
        mapComponentDraggable.setOnTouchListener(new ToolbarIconsTouchListener(mapComponentDraggable.getClass().toString()));

        toolbar.addView(timelineComponentDraggable);
        toolbar.addView(textComponentDraggable);
        toolbar.addView(imageComponentDraggable);
        toolbar.addView(webviewComponentDraggable);
        toolbar.addView(mapComponentDraggable);
    }
}
