package com.mobile.androidtest;

import android.os.Handler;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * author：hj
 * time: 2018/1/2 0002 13:01
 * description:单例模式，调用后会一直存在于内存中。
 */


public class SingleInstance {

    private SingleInstance() {
    }

    private static class SingleInstanceHolder {
        private static final SingleInstance INSTANCE = new SingleInstance();
    }

    public static SingleInstance getInstance() {
        return SingleInstance.SingleInstanceHolder.INSTANCE;
    }


    private WeakReference<TestSingleInstance> contextWeakReference;
    Handler handler = new Handler();


    /**
     * activity不使用弱引用
     * *当Activity关闭时handler未执行完成时：
     * 此时如果GC，由于使用的强引用依赖Activity，Activity不会被释放。
     * 当handler执行完成后，此时GC，Activity内存同样会释放
     *
     * @param activity
     */
    public void toast(final TestSingleInstance activity) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "调用了Toast方法", Toast.LENGTH_LONG).show();
                activity.changeBtnTxt();
            }
        }, 10000);
    }


    /**
     * activity使用弱引用
     * 当Activity关闭时handler未执行完成时：
     * 此时如果GC，由于使用的弱引用依赖Activity，Activity会被释放。
     *
     * @param activity
     */
    public void toastWeakRef(final TestSingleInstance activity) {
        contextWeakReference = new WeakReference<TestSingleInstance>(activity);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TestSingleInstance context1 = contextWeakReference.get();
                if (context1 != null) {
                    Toast.makeText(context1, "调用了Toast方法", Toast.LENGTH_LONG).show();
                    context1.changeBtnTxt();
                }
            }
        }, 10000);
    }
}
