package com.example.tie.mc2.OptionButtons;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.BoardViews.BoardImageView;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 26-Apr-18.
 */

public class OptionsImageTakePhotoButton extends AppCompatButton {
    final BoardImageView targetView;

    public OptionsImageTakePhotoButton(Context context, final BoardImageView targetView) {
        super(context);
        this.targetView = targetView;
        setBackgroundResource(R.drawable.options_image_takephoto);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(0,0,0,10);
        this.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                targetView.takePicture();

            }
        });
    }

}
