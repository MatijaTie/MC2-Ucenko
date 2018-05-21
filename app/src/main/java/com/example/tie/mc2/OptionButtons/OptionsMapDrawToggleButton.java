package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.tie.mc2.BoardViews.BoardMapDrawingLayout;
import com.example.tie.mc2.BoardViews.BoardTextView;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 19-May-18.
 */

public class OptionsMapDrawToggleButton extends ToggleButton {
    final BoardMapDrawingLayout targetView;
    private OptionsMapEraseToggleButton eraser;
    private boolean painting;

    public OptionsMapDrawToggleButton(Context context, final BoardMapDrawingLayout targetView) {
        super(context);
        this.targetView = targetView;
        painting = false;
        setBackgroundResource(R.drawable.options_map_paint_check);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        setLayoutParams(params);
        setText(null);
        setTextOn(null);
        setTextOff(null);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(painting){
                    targetView.setErasing(false);
                    targetView.setPainting(false);
                    painting = false;
                }else{
                    targetView.setPainting(true);
                    painting = true;
                    if(eraser != null){
                        eraser.setChecked(false);
                        eraser.setErasing(false);
                    }
                }

            }
        });
    }

    public void setSibilingButton(OptionsMapEraseToggleButton eraser){
        this.eraser = eraser;
    }

    public void setPainting(boolean b){
        painting = b;
    }

}
