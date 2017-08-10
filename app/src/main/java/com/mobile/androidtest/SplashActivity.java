package com.mobile.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * author：hj
 * time: 2017/8/7 0007 22:27
 */


public class SplashActivity extends Activity {
    private String TAG = "huang";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *
         * 360加固后首次启动。进入其他页面后点击home键退到桌面。再次点击桌面图标，
         * 会重新到SplashActivity。此时!isTaskRoot() == true
         * 杀掉进程后，再次重复相同的操作不会出现该问题(不加固也不会有此问题)
         *
         */
        Log.d(TAG, "onCreate: SplashActivity");
        if (!isTaskRoot()) {
            Log.d(TAG, "onCreate:SplashActivity: !isTaskRoot()");
            finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: SplashActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: SplashActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: SplashActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: SplashActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: SplashActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: SplashActivity");
    }
}
