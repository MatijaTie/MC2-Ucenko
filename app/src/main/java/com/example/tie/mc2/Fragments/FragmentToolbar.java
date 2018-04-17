package com.example.tie.mc2.Fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tie.mc2.Listeners.ToolbarIconsTouchListener;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 12-Mar-18.
 */

public class FragmentToolbar extends Fragment {
    Button testBtn;
    ImageButton testBtn2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toolbar, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testBtn = getView().findViewById(R.id.btn_test);
        testBtn.setOnTouchListener(new ToolbarIconsTouchListener(testBtn.getClass().toString()));

        testBtn2 = getView().findViewById(R.id.btn_test2);
        testBtn2.setOnTouchListener(new ToolbarIconsTouchListener(testBtn2.getClass().toString()));
    }
}
