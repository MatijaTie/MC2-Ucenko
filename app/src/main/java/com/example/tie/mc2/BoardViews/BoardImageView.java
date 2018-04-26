package com.example.tie.mc2.BoardViews;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 18-Apr-18.
 */

public class BoardImageView extends android.support.v7.widget.AppCompatImageView {
    public BoardImageView(Context context, Activity activity) {
        super(context);
        this.setBackgroundResource(R.drawable.ic_trash_full);
    }

    public void takePicture(){

    }

}
