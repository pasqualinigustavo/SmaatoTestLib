package com.smattotest.app.application;

import android.app.Application;
import android.content.Context;

public class STApplication extends Application {

    private static Context context = null;
    public static String TAG = STApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        STApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return STApplication.context;
    }

}


