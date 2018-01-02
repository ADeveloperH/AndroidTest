package com.mobile.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_sys, R.id.btn_cus, R.id.btn_memorytest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sys:
                startActivity(new Intent(this, SysFAActivity.class));
                break;
            case R.id.btn_cus:
                startActivity(new Intent(this, CusFAActivity.class));
                break;
            case R.id.btn_memorytest:
                startActivity(new Intent(this, GlideTest.class));
                break;
        }
    }
}
