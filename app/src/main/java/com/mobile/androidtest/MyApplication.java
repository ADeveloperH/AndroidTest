package com.mobile.androidtest;

import android.app.Application;

/**
 * author：hj
 * time: 2017/7/7 0007 16:17
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获全局崩溃日志
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
