package com.codingblocks.ChatBot.GamePart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codingblocks.customnavigationdrawer.R;

public class GameInitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_init);
        Button yes=(Button) findViewById(R.id.btn_Yes);
        Button no=(Button) findViewById(R.id.btn_No);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(GameInitActivity.this,QuestionActivity.class));
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
