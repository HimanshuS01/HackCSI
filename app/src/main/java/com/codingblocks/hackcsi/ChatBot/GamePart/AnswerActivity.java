package com.codingblocks.ChatBot.GamePart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.codingblocks.customnavigationdrawer.R;


public class AnswerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        TextView textResult = (TextView) findViewById(R.id.scorefinal);
        TextView texttype =(TextView) findViewById(R.id.texttype);

        Bundle b = getIntent().getExtras();

        int score = b.getInt("score");
        int type=b.getInt("type");
        switch (type)
        {
            case 0:texttype.setText("You won !!");
                break;
            case 1:texttype.setText("Wrong Answer !!");
                break;
            case 2:texttype.setText("Sorry, Time up !!");
                break;
            case 3:texttype.setText("Game Finished !!");

        }

        textResult.setText("   Your score : "+ score);

    }

    public void playagain(View o) {
        finish();

        Intent intent = new Intent(this, QuestionActivity.class);

        startActivity(intent);


    }
}