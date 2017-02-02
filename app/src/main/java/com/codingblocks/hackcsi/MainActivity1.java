package com.codingblocks.hackcsi;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        addVideoFragment();
    }

    private void addVideoFragment() {
        ShareVideoFragment shareVideoFragment = new ShareVideoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.facebook_share_button, shareVideoFragment);
        fragmentTransaction.commit();
    }
}
