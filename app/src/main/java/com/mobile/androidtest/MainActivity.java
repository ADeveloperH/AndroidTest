package com.mobile.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mobile.androidtest.pagestate.PageListener;
import com.mobile.androidtest.pagestate.PageStateManager;
import com.mobile.androidtest.pagestate.StringUtils;

public class MainActivity extends AppCompatActivity {

    private PageStateManager pageStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View contentView = findViewById(R.id.tv_content);
        pageStateManager = new PageStateManager.Builder(contentView).build();


        pageStateManager.setPageListener(new PageListener() {
            @Override
            public void onRetry(View retryClickView) {
                Toast.makeText(retryClickView.getContext(), "点击了重试按钮"
                        , Toast.LENGTH_LONG).show();
                myHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 2000);
            }
        });

//        myHandler.sendEmptyMessageDelayed(SHOW_RETYRVIEW, 2000);
        myHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 2000);
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
                    startActivity(new Intent(MainActivity.this,FragmentActivity.class));
                    break;
            }
        }
    };


}
