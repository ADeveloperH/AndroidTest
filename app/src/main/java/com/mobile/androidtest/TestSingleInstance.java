package com.mobile.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2018/1/2 0002 12:53
 * description:
 */


public class TestSingleInstance extends Activity {
    @BindView(R.id.btn_singleton)
    Button btn;
    @BindView(R.id.btn_weakref)
    Button btnWeakref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance);
        ButterKnife.bind(this);
        Log.d("huang", "onCreate: ");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("huang", "onNewIntent: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("huang", "onResume: ");
    }

    public void changeBtnTxt() {
        btn.setText("哈哈哈哈或或");
    }

    @OnClick({R.id.btn_singleton, R.id.btn_weakref, R.id.btn_simple})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_singleton:
                SingleInstance.getInstance().toast(this);
                break;
            case R.id.btn_weakref:
                SingleInstance.getInstance().toastWeakRef(this);
                break;
            case R.id.btn_simple:
                /**
                 * 局部变量：调用完方法后可立即被GC回收
                 * 成员变量：当前Activity会持有引用，调用完方法无法被GC回收，Activity销毁后可以被GC回收
                 */
                SimpleClass simpleClass = new SimpleClass();
                simpleClass.toast(this);
                break;
        }
    }

}
