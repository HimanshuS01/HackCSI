package com.codingblocks.hackcsi.ChatBot.GamePart;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codingblocks.hackcsi.R;


public class QuestionActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    long milibe4;



    Question currentQ;
    TextView txtQuestion, times, scored;
    Button button1, button2, button3;
    CounterClass timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        QuizHelper db = new QuizHelper(this);  // my question bank class
        quesList = db.getAllQuestions();
        Collections.shuffle(quesList);// this will fetch all quetonall questions
        currentQ = quesList.get(qid); // the current question

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed

        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);

        // the timer
        times = (TextView) findViewById(R.id.timers);


        // method which will set the things up for our game
        setQuestionView();
        times.setText("00:02:00");

        // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
         timer = new CounterClass(60000, 1000);
        timer.start();

        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // passing the button text to other method
                // to check whether the anser is correct or not
                // same for all three buttons
                getAnswer(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button3.getText().toString());
            }
        });
    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {

            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            scored.setText("Score : " + score);
        } else {

            // if unlucky start activity and finish the game

            timer.cancel();
            timer=null;

            Intent intent = new Intent(QuestionActivity.this,
                    AnswerActivity.class);


            // passing the int value
            Bundle b = new Bundle();
            b.putInt("score", score);
            b.putInt("type",1);
            // code 1 denotes wrong answer
            intent.putExtras(b); // Put your score to your next
            finish();
            startActivity(intent);

        }
        if (qid < 10) {

            // if questions are not over then do this
            currentQ = quesList.get(qid);
            setQuestionView();
        } else {

            // if over do this
            timer.cancel();
            timer=null;

            finish();
            Intent intent = new Intent(QuestionActivity.this,
                    AnswerActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);// Your score
            b.putInt("type",0);  //0 denotes questions finished
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);

        }


    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }

        @Override
        public void onFinish() {

                timer.cancel();
                timer=null;
                times.setText("Time is up");

            Intent intent = new Intent(QuestionActivity.this,
                    AnswerActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);// Your score
            b.putInt("type",2);  //2 denotes times up
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();



        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

         long    millis = millisUntilFinished;
            milibe4=millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);
        }


    }


    private void setQuestionView() {

        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());

        qid++;
    }

    @Override
    protected void onPause() {

        if(timer!=null)
        {
            timer.cancel();
            timer=null;

        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        if(timer==null)
        {
        timer=new CounterClass(milibe4,1000);
            timer.start();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (timer!=null)
        timer.cancel();
        timer=null;
        finish();
        Intent intent = new Intent(QuestionActivity.this,
                AnswerActivity.class);
        Bundle b = new Bundle();
        b.putInt("score", score);// Your score
        b.putInt("type",3);  //0 denotes questions finished
        intent.putExtras(b); // Put your score to your next
        startActivity(intent);
        super.onBackPressed();
    }
}

