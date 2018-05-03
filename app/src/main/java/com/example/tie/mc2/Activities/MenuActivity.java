package com.example.tie.mc2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

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
    }
}
