package com.mobile.androidtest;


import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 查看任务栈activity信息
 * adb shell dumpsys activity activities | sed -En -e '/Running activities/,/Run #0/p'
 *
 * http://blog.csdn.net/xx326664162/article/details/52385720
 *
 * https://github.com/OONullPointerAlex/awesome-adb#%E6%9F%A5%E7%9C%8B%E5%89%8D%E5%8F%B0-activity
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MainActivity.this.hashCode();
    }
}
