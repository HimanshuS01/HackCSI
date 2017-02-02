package com.codingblocks.hackcsi;

import android.app.Application;
import android.content.Context;

/**
 * Created by sachinaggarwal on 02/02/17.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}