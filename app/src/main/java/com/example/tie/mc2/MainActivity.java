package com.example.tie.mc2;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.Fragments.FragmentToolbar;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String FRAGMENT_BOARD_MAIN = "1";
    public static final String FRAGMENT_TOOLBAR_MAIN = "2";
    public static int CAMERA_REQUEST = 246;
    public static int IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){                                                                      //izbjegacanje null pointera
            actionBar.hide();                                                                       //sakrij action bar
        }
        //Inicijalizacija Viewa


        //Inicijalizacija fragmenta
        FragmentBoard boardFragment = new FragmentBoard();
        FragmentToolbar toolbarFragment = new FragmentToolbar();

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

}
