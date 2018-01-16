package com.mobile.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.homenotifyview)
    HomeNormalNotifyView homenotifyview;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        homenotifyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homenotifyview.hiddlenNotifyView();
            }
        });

    }

    @OnClick({R.id.btn_show,R.id.btn_show2, R.id.btn_hiddlen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                homenotifyview.showNotifyView();
                break;
            case R.id.btn_show2:
                startActivity(new Intent(context, UseHelpViewActivity.class));
                context.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
            case R.id.btn_hiddlen:
                homenotifyview.hiddlenNotifyView();
                break;
        }
    }

}
