package com.example.tie.mc2.ToolbarComponents;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

/**
 * Created by Tie on 14-May-18.
 */

public class WebviewComponentDraggable extends View {
    LinearLayout.LayoutParams params;

    public WebviewComponentDraggable(Context context) {
        super(context);
        setBackgroundResource(R.drawable.toolbar_component_draggable_webview);
        params = new LinearLayout.LayoutParams(125,125);
        params.gravity = Gravity.CENTER;
        params.setMargins(0,5,0,5);
        setLayoutParams(params);
    }
}
