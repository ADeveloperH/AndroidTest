package com.mobile.androidtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mobile.androidtest.pagestate.PageStateManager;

public class MainActivity2 extends AppCompatActivity {

    private PageStateManager pageStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        View contentView = findViewById(R.id.tv_content);
//        pageStateManager = new PageStateManager(contentView);
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
//        myHandler.sendEmptyMessageDelayed(SHOW_RETYRVIEW, 2000);
    }

    public static final int SHOW_RETYRVIEW = 1;
    public static final int SHOW_CONTENT = 2;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RETYRVIEW:
                    pageStateManager.showRetryView();
                    break;
                case SHOW_CONTENT:
                    pageStateManager.showContent();
                    break;
            }
        }
    };


}
