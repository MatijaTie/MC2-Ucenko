package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.tie.mc2.BoardViews.BoardMapDrawingLayout;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 19-May-18.
 */

public class OptionsMapEraseToggleButton extends ToggleButton {
    final BoardMapDrawingLayout targetView;
    private boolean erasing;
    private OptionsMapDrawToggleButton draw;

    public OptionsMapEraseToggleButton(Context context, final BoardMapDrawingLayout targetView) {
        super(context);
        this.targetView = targetView;
        erasing = false;
        setBackgroundResource(R.drawable.options_map_erase_check);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        setLayoutParams(params);
        setText(null);
        setTextOn(null);
        setTextOff(null);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(erasing){
                    targetView.setErasing(false);
                    targetView.setPainting(false);
                    erasing = false;
                }else{
                    targetView.setErasing(true);
                    erasing = true;
                    if(draw != null){
                        draw.setChecked(false);
                        draw.setPainting(false);
                    }
                }

            }
        });
    }

    public void setSibilingButton(OptionsMapDrawToggleButton draw){
        this.draw = draw;
    }

    public void setErasing(boolean b) {
        erasing = b;
    }

}