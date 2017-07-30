package com.mobile.androidtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * author：hj
 * time: 2017/7/30 0030 09:24
 *
 * 可以收到时间和时区改变的广播。
 * 进程被杀后无法收到。
 * 测试：三星note5
 */


public class TimeChangedReceiver extends BroadcastReceiver {
    private String TAG = "huang";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (Intent.ACTION_TIME_CHANGED.equals(action)) {
                Log.d(TAG, "onReceive: Intent.ACTION_TIME_CHANGED");
                Toast.makeText(context, "Intent.ACTION_TIME_CHANGED.", Toast.LENGTH_LONG).show();
            } else if (Intent.ACTION_TIMEZONE_CHANGED.equals(action)) {
                Log.d(TAG, "onReceive: Intent.ACTION_TIMEZONE_CHANGED");
                Toast.makeText(context, "Intent.ACTION_TIMEZONE_CHANGED.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
