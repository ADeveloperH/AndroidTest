package com.mobile.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.homenotifyview)
    HomeNotifyView homenotifyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_show, R.id.btn_hiddlen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                homenotifyview.showNotifyView();
                break;
            case R.id.btn_hiddlen:
                homenotifyview.hiddlenNotifyView();
                break;
        }
    }
}
