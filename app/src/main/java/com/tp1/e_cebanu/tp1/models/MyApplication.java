package com.tp1.e_cebanu.tp1.models;

import android.app.Application;
import android.content.Context;

/**
 * Created by e_cebanu on 3/6/2017.
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
