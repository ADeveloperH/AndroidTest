package com.mobile.androidtest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

/**
 * author：hj
 * time: 2017/9/24 0024 21:38
 */


public class ServiceOne extends Service {

    private MyBinder mBinder = new MyBinder();

    static class MyBinder extends Binder {
        public void startDownload() {
            Log.d("huang", "startDownload: ");
            //创建线程执行耗时操作
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }).start();
        }
    }

    IMyAidlServiceOne.Stub mBinderStub = new IMyAidlServiceOne.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            return TextUtils.isEmpty(str) ? null : str.toUpperCase();
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("有通知到来")
                .setContentTitle("这是通知的标题")
                .setContentText("这是通知的内容")
                .setContentIntent(pendingIntent)
                .build();
        //开启前台服务。提高优先级
        startForeground(1, notification);

        Log.d("huang", "onCreate: ");
        //main主线程
        Log.d("huang", "onCreate: Service_ThreadName:" + Thread.currentThread().getName());
        Log.d("huang", "ServiceOne: ProcessPid:" + Process.myPid());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("huang", "onStartCommand: intent:" + (intent == null));
        Log.d("huang", "onStartCommand: flags:" + flags + " startId:" + startId);
        int startCommand = super.onStartCommand(intent, flags, startId);
        //public static final int START_STICKY = 1;
        Log.d("huang", "onStartCommand: startCommand:" + startCommand);
        return startCommand;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("huang", "onStart: intent:" + (intent == null) + " startId:" + startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("huang", "onBind: intent:" + (intent == null));
        return mBinderStub;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁。
        Log.d("huang", "onDestroy: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("huang", "onUnbind: intent:" + (intent == null));
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d("huang", "onRebind: intent:" + (intent == null));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("huang", "onConfigurationChanged: newConfig:" + (newConfig == null));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("huang", "onLowMemory: ");
    }

}
