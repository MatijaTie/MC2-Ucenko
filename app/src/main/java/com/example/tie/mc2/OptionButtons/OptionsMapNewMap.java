package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.BoardViews.BoardMapDrawingLayout;
import com.example.tie.mc2.BoardViews.RootView;
import com.example.tie.mc2.Dialogues.MapChangeDialogue;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 19-May-18.
 */

public class OptionsMapNewMap extends AppCompatButton {
    final BoardMapDrawingLayout targetView;

    public OptionsMapNewMap(Context context, final BoardMapDrawingLayout targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_map_new_map);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MapChangeDialogue dialogue = new MapChangeDialogue(getContext(), targetView);
                dialogue.show();
            }
        });
    }

}