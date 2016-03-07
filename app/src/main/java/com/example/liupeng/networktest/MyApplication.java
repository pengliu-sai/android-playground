package com.example.liupeng.networktest;

import android.app.Application;
import android.content.Context;

/**
 * Created by liupeng on 16/3/6.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
