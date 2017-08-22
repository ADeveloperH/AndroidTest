package com.mobile.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_disklrucache)
    Button btnDisklrucache;
    @BindView(R.id.btn_okhttp)
    Button btnOkhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_disklrucache, R.id.btn_okhttp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_disklrucache:
                startActivity(new Intent(this,DiskLruCacheActivity.class));
                break;
            case R.id.btn_okhttp:
                startActivity(new Intent(this,OkHttpActivity.class));
                break;
        }
    }
}
