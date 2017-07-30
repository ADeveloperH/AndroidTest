package com.mobile.androidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         *
         * SystemClock.elapsedRealtime()获取系统启动开机的时间。可以用来统计时间间隔
         * 1、获取服务器时间S1
         * 2、获取当时的开机时间C1
         * 问题：处理相关逻辑后需要知道现在的服务器时间S2
         * 3、获取现在的开机时间C2
         * 4、C2-C1=S2-S1。（利用时间间隔一致来保证获取S2的准确性）
         * 最终公式：现在的服务时间=S1+(C2-C1)
         *
         * 遗留问题：如何正确的获取服务器S1的时间
         * 思路：1、有网状态获取网络时间
         *      2、无网络状态：请求失败。提示用户需要网络。
         *      3、请求接口时服务器发现时间间隔较大，返回错误码，并返回正确时间，用户客户端同步校验。
         */
    }
}
