package com.example.tie.mc2.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tie.mc2.BoardViews.BoardDrawingLayout;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 13-May-18.
 */

public class ColorChangeDialogue extends Dialog {
    LinearLayout mainLayout;
    Button okButton, cancelButton;
    SeekBar seekBar1, seekBar2, seekBar3;
    private int color;
    TextView colorHolder;
    BoardDrawingLayout targetView;

    public ColorChangeDialogue(@NonNull Context context, BoardDrawingLayout layout) {
        super(context);
        this.color = layout.getDrawingColor();
        targetView = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_color_modifier);
        mainLayout = findViewById(R.id.dialogue_text_main_layout);
        okButton = findViewById(R.id.dialogue_color_ok_button);
        cancelButton = findViewById(R.id.dialogue_color_cancel_button);
        seekBar1 = findViewById(R.id.dialogue_color_seek_bar1);
        seekBar2 = findViewById(R.id.dialogue_color_seek_bar2);
        seekBar3 = findViewById(R.id.dialogue_color_seek_bar3);
        colorHolder = findViewById(R.id.dialogue_color_show);
        colorHolder.setBackgroundColor(color);
        initSeekBar();
        setSeekbarProgress();
        setConfirmButtons();
    }

    public void initSeekBar(){
        seekBar1.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar2.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seekBar3.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        seekBar1.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar2.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seekBar3.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        seekBar1.setMax(255);
        seekBar2.setMax(255);
        seekBar3.setMax(255);
        setColorChangeListeners();
    }

    private void setColorChangeListeners(){
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeColor();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeColor();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeColor();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void changeColor(){
        colorHolder.setBackgroundColor(Color.rgb(seekBar1.getProgress(), seekBar2.getProgress(), seekBar3.getProgress()));
    }

    private void setSeekbarProgress(){
        int red, green, blue;


        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);

        seekBar1.setProgress(red);
        seekBar2.setProgress(green);
        seekBar3.setProgress(blue);
    }

    public void setConfirmButtons(){
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable background = colorHolder.getBackground();
                if (background instanceof ColorDrawable) {
                    int color = ((ColorDrawable) background).getColor();
                    targetView.setDrawingColor(color);
                }
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
