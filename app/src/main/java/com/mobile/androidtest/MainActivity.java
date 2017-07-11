package com.mobile.androidtest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String TAG = "hhh";

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "" + UIUtils.getNavHeight(this), 1).show();
        System.out.println("Nav:" + UIUtils.getNavHeight(this));
    }
}
