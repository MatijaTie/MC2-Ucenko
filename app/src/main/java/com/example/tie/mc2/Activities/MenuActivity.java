package com.example.tie.mc2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.tie.mc2.LoadingTask;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 03-May-18.
 */

public class MenuActivity extends AppCompatActivity{
    LinearLayout newBoardButton, loadBoardButton, optionsButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        newBoardButton = findViewById(R.id.menu_nova_ploca_button);
        newBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        loadBoardButton = findViewById(R.id.menu_otvori_plocu_button);
        loadBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                selectFileIntent.setType("application/.tie");
                startActivityForResult(Intent.createChooser(selectFileIntent, "Izaberi file"), 4567);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 4567 && data != null) {
            Intent intent = new Intent(this, BoardActivity.class);
            intent.putExtra("data",data);
            startActivity(intent);
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }
}
