package com.mobile.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/7/6 0006 11:07
 */


public class BaseActivity extends Activity {
    final String TAG = "activity";
    final String className = this.getClass().getSimpleName();
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btn_A)
    Button btnA;
    @BindView(R.id.btn_B)
    Button btnB;
    @BindView(R.id.btn_C)
    Button btnC;
    @BindView(R.id.btn_D)
    Button btnD;
    @BindView(R.id.btn_main)
    Button btnMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        text.setText(className);
        Log.d(TAG, className + ":::onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, className + ":::onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, className + ":::onResume");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, className + ":::onNewIntent");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, className + ":::onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, className + ":::onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, className + ":::onDestroy");
    }

    @OnClick({R.id.btn_A, R.id.btn_B, R.id.btn_C, R.id.btn_D, R.id.btn_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_A:
                startActivity(new Intent(this, ActivityA.class));
                break;
            case R.id.btn_B:
                startActivity(new Intent(this, ActivityB.class));
                break;
            case R.id.btn_C:
                startActivity(new Intent(this, ActivityC.class));
                break;
            case R.id.btn_D:
                startActivity(new Intent(this, ActivityD.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
