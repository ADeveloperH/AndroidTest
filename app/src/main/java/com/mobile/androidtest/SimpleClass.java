package com.mobile.androidtest;

import android.widget.Toast;

/**
 * author：hj
 * time: 2018/1/2 0002 22:01
 * description:
 */


public class SimpleClass {

    /**
     * 非单例模式。new对象后调用方法
     * @param activity
     */
    public void toast(TestSingleInstance activity) {
        Toast.makeText(activity, "调用了Toast方法", Toast.LENGTH_LONG).show();
        activity.changeBtnTxt();
    }
}
