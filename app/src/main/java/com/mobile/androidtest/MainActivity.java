package com.mobile.androidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *启动页底部logo被导航栏遮挡。
 *  解决方案：这个问题只有在5.0+（21）才会出现。
 *  在values-v21/styles.xml样式中启动页样式设置
 *  ：<item name="android:windowDrawsSystemBarBackgrounds">false</item>
 *  参考：https://juejin.im/entry/57fe42a8da2f60004fb4c311
 *  https://stackoverflow.com/questions/40814657/can-a-windowbackground-be-positioned-below-the-status-bar-above-the-navigation
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
