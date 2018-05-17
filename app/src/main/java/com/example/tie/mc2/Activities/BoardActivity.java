package com.example.tie.mc2.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
}
