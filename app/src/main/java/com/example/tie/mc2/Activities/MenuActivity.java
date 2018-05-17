package com.example.tie.mc2.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.tie.mc2.R;

import static com.example.tie.mc2.StaticValues.DataKeys.LOAD_REQUEST;

/**
 * Created by Tie on 03-May-18.
 */

public class MenuActivity extends AppCompatActivity{
    LinearLayout newBoardButton, loadBoardButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

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
                startActivityForResult(Intent.createChooser(selectFileIntent, "Izaberi file"), LOAD_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOAD_REQUEST && data != null) {
            Intent intent = new Intent(this, BoardActivity.class);
            intent.putExtra("data",data);
            startActivity(intent);
        }
    }
}
