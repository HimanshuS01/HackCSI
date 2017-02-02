package com.codingblocks.ChatBot.Initial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.codingblocks.ChatBot.ChatbotFiles.ChatActivity;
import com.codingblocks.customnavigationdrawer.R;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        changeStatusBarColor();
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sp.getBoolean("firstrun",false))
        {
            finish();
            Intent intent = new Intent(this, ChatActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);


        }
        else {
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("firstrun", true).apply();
            Intent intent = new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
            finish();
        }
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
