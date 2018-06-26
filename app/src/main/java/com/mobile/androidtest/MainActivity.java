package com.mobile.androidtest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mobile.androidtest.pagestate.PageStateManager;
import com.mobile.androidtest.pagestate.StringUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private PageStateManager pageStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("huang", "onCreate: ");
        ButterKnife.bind(this);



//        View contentView = findViewById(R.id.tv_content);
//        pageStateManager = new PageStateManager.Builder(contentView).build();
//
//
//        pageStateManager.setPageListener(new PageListener() {
//            @Override
//            public void onRetry(View retryClickView) {
//                Toast.makeText(retryClickView.getContext(), "点击了重试按钮"
//                        , Toast.LENGTH_LONG).show();
//                myHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 2000);
//            }
//        });
//
////        myHandler.sendEmptyMessageDelayed(SHOW_RETYRVIEW, 2000);
//        myHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 2000);
    }

    public static final int SHOW_RETYRVIEW = 1;
    public static final int SHOW_CONTENT = 2;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RETYRVIEW:
                    String info = "哎呀\n网络好像不太给力，请稍后再试";

                    pageStateManager.showRetryView(StringUtils.formatRelativeSize(info, 1.4f, 0, 2));
                    break;
                case SHOW_CONTENT:
                    pageStateManager.showContent();
//                    finish();
                    startActivity(new Intent(MainActivity.this, FragmentActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("huang", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("huang", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("huang", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("huang", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("huang", "onDestroy: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("huang", "onConfigurationChanged: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("huang", "onSaveInstanceState: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("huang", "onRestart: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("huang", "onRestoreInstanceState: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("huang", "onNewIntent: ");
    }

    @OnClick(R.id.btn)
    public void onClick() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
