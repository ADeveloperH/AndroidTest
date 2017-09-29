package com.mobile.androidtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d("huang", "MainActivity: ProcessPid:" + Process.myPid());
    }

    @OnClick({R.id.btn_start_service1, R.id.btn_stop_service1, R.id.btn_bind_service1, R.id.btn_unbind_service1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service1:
                Intent startServiceIntent = new Intent(this, ServiceOne.class);
                startService(startServiceIntent);
                break;
            case R.id.btn_stop_service1:
                /**
                 * 只会让Service停止{@link ServiceOne#onDestroy()}
                 */
                Intent stopServiceIntent = new Intent(this, ServiceOne.class);
                stopService(stopServiceIntent);
                break;
            case R.id.btn_bind_service1:
                Intent bindIntent = new Intent(this, ServiceOne.class);
                //BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
                // 这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service1:
                /**
                 * 只会让Service和Activity解除关联{@link ServiceOne#onDestroy()}
                 */
                unbindService(serviceConnection);
                break;
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            ServiceOne.MyBinder myBinder = (ServiceOne.MyBinder) service;
//            myBinder.startDownload();
            try {
                IMyAidlServiceOne iMyAidlServiceOne = IMyAidlServiceOne.Stub.asInterface(service);
                Log.d("huang", "onServiceConnected: 1+2:" + iMyAidlServiceOne.plus(1, 2));
                Log.d("huang", "onServiceConnected: abc toUpper:" + iMyAidlServiceOne.toUpperCase("abc"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("huang", "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("huang", "onServiceDisconnected: ");
        }
    };

}
