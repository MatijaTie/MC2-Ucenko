package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.BoardViews.BoardImageView;
import com.example.tie.mc2.BoardViews.BoardMapDrawingLayout;
import com.example.tie.mc2.Dialogues.ColorChangeDialogue;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 20-May-18.
 */

public class OptionsMapChangeColorButton extends AppCompatButton {
    final BoardMapDrawingLayout targetView;

    public OptionsMapChangeColorButton(final Context context, final BoardMapDrawingLayout targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_map_change_color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorChangeDialogue dialogue = new ColorChangeDialogue(context, targetView);
                dialogue.show();

            }
        });
    }

}
