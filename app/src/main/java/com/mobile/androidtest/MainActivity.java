package com.mobile.androidtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnGetBug;
    private Button btnFixBug;
    private BugTest bugTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnGetBug = (Button) findViewById(R.id.btn_getbug);
        btnFixBug = (Button) findViewById(R.id.btn_fixbug);

        btnGetBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bugTest == null) {
                    bugTest = new BugTest();
                }
                bugTest.getBug(MainActivity.this);
            }
        });

        btnFixBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FixDexUtil.isGoingToFix(MainActivity.this)) {
                    Toast.makeText(v.getContext(), "开始修复bug", Toast.LENGTH_LONG).show();
                    FixDexUtil.loadFixedDex(MainActivity.this, Environment.getExternalStorageDirectory());
                }

            }
        });
    }
}
