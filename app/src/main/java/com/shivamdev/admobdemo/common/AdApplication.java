package com.shivamdev.admobdemo.common;

import android.app.Application;

/**
 * Created by shivam on 25/10/16.
 */

public class AdApplication extends Application {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
