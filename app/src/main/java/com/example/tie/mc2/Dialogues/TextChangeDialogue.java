package com.example.tie.mc2.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.BoardTextView;
import com.example.tie.mc2.R;

import java.util.ArrayList;

/**
 * Created by Tie on 28-Apr-18.
 */

public class TextChangeDialogue extends Dialog{
    SeekBar seekBar1, seekBar2, seekBar3;
    TextView textHolder;
    View targetTextSize, targetTextColor, targetBackgroundColor;
    FrameLayout holderTextSize, holderTextColor, holderBackgroundColor;
    ArrayList<FrameLayout> holders;
    LinearLayout mainLayout;
    CheckBox transparentCheckBox;
    Button okButton, cancelButton;

    int textSize, textColor, backgroundColor;
    View currentlySelected;
    BoardTextView targetView;


    public TextChangeDialogue(@NonNull Context context, BoardTextView targetView) {
        super(context);
        this.textSize = targetView.getCustomTextSize();
        this.textColor = targetView.getTextColor();
        this.backgroundColor = targetView.getBackgroundColor();
        this.targetView = targetView;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_text_modifier);

        initialize();

    }

    public void initialize(){
        mainLayout = findViewById(R.id.dialogue_text_main_layout);

        okButton = findViewById(R.id.dialogue_text_ok_button);
        cancelButton = findViewById(R.id.dialogue_text_cancel_button);
        setConfirmButtons();

        transparentCheckBox = findViewById(R.id.dialogue_transparent_checkbox);
        transparentCheckBox.setVisibility(View.INVISIBLE);
        if(backgroundColor == 0){
            transparentCheckBox.setChecked(true);
        }
        setCheckboxListener();

        textHolder = findViewById(R.id.dialogue_text_text);
        textHolder.setBackgroundColor(backgroundColor);
        textHolder.setTextColor(textColor);
        textHolder.setTextSize(textSize);

        holderTextSize = findViewById(R.id.dialogue_text_textsize_holder);
        holderTextColor = findViewById(R.id.dialogue_text_textcolor_holder);
        holderBackgroundColor = findViewById(R.id.dialogue_text_backgroundcolor_holder);

        targetTextSize = findViewById(R.id.dialogue_text_textsize);
        targetTextColor = findViewById(R.id.dialogue_text_textcolor);
        targetBackgroundColor = findViewById(R.id.dialogue_text_backgroundcolor);

        holders = new ArrayList<>();
        holders.add(holderBackgroundColor);
        holders.add(holderTextColor);
        holders.add(holderTextSize);

        targetTextSize.setFocusable(true);
        targetTextColor.setFocusable(true);
        targetBackgroundColor.setFocusable(true);

        seekBar1 = findViewById(R.id.dialogue_text_seek_bar1);
        seekBar2 = findViewById(R.id.dialogue_text_seek_bar2);
        seekBar3 = findViewById(R.id.dialogue_text_seek_bar3);

        seekBar1.getLayoutParams().height = 0;
        seekBar2.getLayoutParams().height = 0;
        seekBar3.getLayoutParams().height = 0;

        seekBar1.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        seekBar1.getThumb().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        setAllClickListeners();
    }

    public void setCheckboxListener(){
        transparentCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transparentCheckBox.isChecked()){
                    textHolder.setBackgroundColor(0);
                }else{
                    textHolder.setBackgroundColor(Color.rgb(seekBar1.getProgress(),seekBar2.getProgress(),seekBar3.getProgress()));
                }
            }
        });
    }

    public void setTargetBackground(FrameLayout holder){
        for(View v : holders){
            if(v.equals(holder)){
                v.setBackgroundResource(R.drawable.dialogue_options_holder_background);
                currentlySelected = v;
            }else{
                v.setBackground(null);
            }
        }
    }

    public void setSearchBarsVisibility(View v){
        LinearLayout.LayoutParams params1, params2, params3;
        int hiddenHeigth, shownHeigth;

        hiddenHeigth = (int) getContext().getResources().getDimension(R.dimen.dialogue_seekbar_hidden);
        shownHeigth = LinearLayout.LayoutParams.WRAP_CONTENT;

        params1 = (LinearLayout.LayoutParams) seekBar1.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) seekBar2.getLayoutParams();
        params3 = (LinearLayout.LayoutParams) seekBar3.getLayoutParams();

        if(v.equals(targetTextSize)){
            params1.height = shownHeigth;
            params2.height = hiddenHeigth;
            params3.height = hiddenHeigth;
            seekBar1.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            seekBar1.getThumb().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            setBarsForTextResize();
        }else{
            params1.height = shownHeigth;
            params2.height = shownHeigth;
            params3.height = shownHeigth;
            seekBar1.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            seekBar2.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            seekBar3.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            seekBar1.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            seekBar2.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            seekBar3.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            setBarsForColorChange();
        }
        seekBar1.setLayoutParams(params1);
        seekBar2.setLayoutParams(params2);
        seekBar3.setLayoutParams(params3);

    }

    private void setTextResizeListeners(){
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeText();
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

    private void setColorChangeListeners(final View v){
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeColor(v);
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
                    changeColor(v);
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
                    changeColor(v);
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

    public void setAllClickListeners(){
        targetTextSize.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                transparentCheckBox.setVisibility(View.INVISIBLE);
                currentlySelected = targetTextSize;
                setTargetBackground(holderTextSize);
                setSearchBarsVisibility(targetTextSize);
                setSeekbarProgress(v);
                setTextResizeListeners();

                return false;
            }
        });

        targetTextColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                transparentCheckBox.setVisibility(View.INVISIBLE);
                currentlySelected = targetTextColor;
                setTargetBackground(holderTextColor);
                setSearchBarsVisibility(targetTextColor);
                setSeekbarProgress(v);
                setColorChangeListeners(v);

                return false;
            }
        });

        targetBackgroundColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                transparentCheckBox.setVisibility(View.VISIBLE);
                currentlySelected = targetBackgroundColor;
                setTargetBackground(holderBackgroundColor);
                setSearchBarsVisibility(targetBackgroundColor);
                setSeekbarProgress(v);
                setColorChangeListeners(v);

                return false;
            }
        });
    }

    public void changeText(){
        textHolder.setTextSize(seekBar1.getProgress());
    }
    private void changeColor(View v){
        if(v.equals(targetBackgroundColor)){
            if(transparentCheckBox.isChecked()){
                textHolder.setBackgroundColor(0);
            }else{
                textHolder.setBackgroundColor(Color.rgb(seekBar1.getProgress(), seekBar2.getProgress(), seekBar3.getProgress()));
            }

        }else if(v.equals(targetTextColor)){
            textHolder.setTextColor(Color.rgb(seekBar1.getProgress(), seekBar2.getProgress(), seekBar3.getProgress()));
        }
    }

    private void setBarsForTextResize(){
        seekBar1.setMax(50);
    }

    private void setBarsForColorChange(){
        seekBar1.setMax(255);
        seekBar2.setMax(255);
        seekBar3.setMax(255);
    }

    private void setSeekbarProgress(View v){
        int color;
        int red, green, blue;

        if(v.equals(targetTextSize)){
            seekBar1.setProgress((int) pixelsToSp(getContext(),textHolder.getTextSize()));

        }else if(v.equals(targetTextColor)){
            color = textHolder.getCurrentTextColor();
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);

            seekBar1.setProgress(red);
            seekBar2.setProgress(green);
            seekBar3.setProgress(blue);

        }else if(v.equals(targetBackgroundColor)){
            Drawable background = textHolder.getBackground();
                if (background instanceof ColorDrawable) {
                    color = ((ColorDrawable) background).getColor();
                    if (color == 0) {
                        color = 16777215;
                    }
                    red = Color.red(color);
                    green = Color.green(color);
                    blue = Color.blue(color);
                    seekBar1.setProgress(red);
                    seekBar2.setProgress(green);
                    seekBar3.setProgress(blue);

            }

        }

    }
    private float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    public void setConfirmButtons(){
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable background = textHolder.getBackground();
                if (background instanceof ColorDrawable) {
                    int color = ((ColorDrawable) background).getColor();
                    targetView.setCustomBackgroundColor(color);
                    targetView.setBackgroundColor(color);
                }
                targetView.setTextSize(pixelsToSp(getContext(),textHolder.getTextSize()));
                targetView.setCustomTextSize((int) pixelsToSp(getContext(),textHolder.getTextSize()));

                targetView.setTextColor(textHolder.getCurrentTextColor());
                targetView.setCustomTextColor(textHolder.getCurrentTextColor());
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
