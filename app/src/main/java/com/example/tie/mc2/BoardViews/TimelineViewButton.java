package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 22-Apr-18.
 */

public class TimelineViewButton extends FrameLayout {
    Button timelineButton;
    private boolean isSmall;

    public TimelineViewButton(Context context) {
        super(context);

        inflate(context, R.layout.component_timline_layout,  this);
        isSmall = false;
        timelineButton = findViewById(R.id.timelineBtn);
        bringToFront();
        bringThisToFront();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "klik", Toast.LENGTH_SHORT).show();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timelineButton.getLayoutParams();
               if(isSmall){
                   params.height = 100;
                   params.width = 100;
                   params.topMargin = 0;
                   isSmall = false;
               }else{
                   params.height = 75;
                   params.width = 75;
                   params.topMargin = 50;
                   isSmall = true;
               }
                timelineButton.setLayoutParams(params);
            }
        });
       // setBackgroundResource(R.drawable.component_timline_timestamp);
    }



    public void bringThisToFront(){
        bringToFront();
    }
}
