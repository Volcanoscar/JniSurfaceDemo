package com.android.myapplication;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/10/9.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
