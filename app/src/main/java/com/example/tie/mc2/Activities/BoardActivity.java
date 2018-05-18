package com.example.tie.mc2.Activities;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.RootView;
import com.example.tie.mc2.BoardViews.ViewBuilder;
import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.Fragments.FragmentToolbar;
import com.example.tie.mc2.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.ButterKnife;

import static com.example.tie.mc2.StaticValues.DataKeys.FRAGMENT_BOARD_MAIN;
import static com.example.tie.mc2.StaticValues.DataKeys.FRAGMENT_TOOLBAR_MAIN;

public class BoardActivity extends AppCompatActivity {



    FragmentBoard boardFragment;
    FragmentToolbar toolbarFragment;

    private ArrayList<RootView> childRootViews;
    ProgressBar progressBarLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        progressBarLine = findViewById(R.id.progressBarLine);


        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){                                                                      //izbjegacanje null pointera
            actionBar.hide();                                                                       //sakrij action bar
        }
        Bundle bundledData = getIntent().getExtras();
        Intent data = null;
        if (bundledData != null) {
            data = (Intent) bundledData.get("data");
        }
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
        }

        //Inicijalizacija fragmenta
        boardFragment = FragmentBoard.newInstance(uri);
        toolbarFragment = new FragmentToolbar();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        fragmentTransaction.add(R.id.holder_main_board, boardFragment, FRAGMENT_BOARD_MAIN);
        fragmentTransaction.add(R.id.holder_main_toolbar, toolbarFragment, FRAGMENT_TOOLBAR_MAIN)
                .commit();

    }

    private void adjustKeyboardKeyHeight (MyKeyboard keyboard, int newKeyHeight) {
        int oldKeyHeight = keyboard.getKeyHeight();
        int verticalGap = keyboard.getVerticalGap();
        int rows = 0;
        for (Keyboard.Key key : keyboard.getKeys()) {
            key.height = newKeyHeight;
            int row = (key.y + verticalGap) / (oldKeyHeight + verticalGap);
            key.y = row * newKeyHeight + (row - 1) * verticalGap;
            rows = Math.max(rows, row + 1);
        }
        keyboard.setHeight(rows * newKeyHeight + (rows - 1) * verticalGap);
    }

    private static class MyKeyboard extends Keyboard {
        private int height;
        MyKeyboard (Context context, int xmlLayoutResId) {
            super(context, xmlLayoutResId);
            height = super.getHeight();
        }
        @Override public int getKeyHeight() {
            return super.getKeyHeight();
        }
        @Override public int getVerticalGap() {
            return super.getVerticalGap();
        }
        public void setHeight (int newHeight) {
            height = newHeight;
        }
        @Override public int getHeight() {
            return height;
        }
    }
}
