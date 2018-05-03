package com.example.tie.mc2.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.Fragments.FragmentToolbar;
import com.example.tie.mc2.R;

import butterknife.ButterKnife;

public class BoardActivity extends AppCompatActivity {
    public static final String FRAGMENT_BOARD_MAIN = "1";
    public static final String FRAGMENT_TOOLBAR_MAIN = "2";
    public static int CAMERA_REQUEST = 246;
    public static int IMAGE_REQUEST = 22;

    FragmentBoard boardFragment;
    FragmentToolbar toolbarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.bind(this);
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){                                                                      //izbjegacanje null pointera
            actionBar.hide();                                                                       //sakrij action bar
        }
        //Inicijalizacija Viewa


        //Inicijalizacija fragmenta
        boardFragment = new FragmentBoard();
        toolbarFragment = new FragmentToolbar();

        //Dodavanje fragmenata
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.holder_main_board, boardFragment, FRAGMENT_BOARD_MAIN);
        fragmentTransaction.add(R.id.holder_main_toolbar, toolbarFragment, FRAGMENT_TOOLBAR_MAIN)
                .commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.d("camera request activity",resultCode +"="+CAMERA_REQUEST);
    }

    public void save(){

    }

}
