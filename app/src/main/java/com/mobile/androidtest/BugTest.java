package com.mobile.androidtest;

import android.content.Context;
import android.widget.Toast;

/**
 * @author huangjian
 * @create 2018/7/11 0011
 * @Description
 */
public class BugTest {

    public void getBug(Context context) {
        int i = 0;
        int j = 10;
        Toast.makeText(context, "result is " + (j / i), Toast.LENGTH_LONG).show();
    }
}
