package com.mobile.androidtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.R.attr.scrollY;

public class MainActivity extends AppCompatActivity {

    private ObservableWebView webView;
    private String TAG = "huang";
    private FloatingView floatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (ObservableWebView) findViewById(R.id.scorllableWebview);
        floatingView = (FloatingView) findViewById(R.id.floatview);

        webView.loadUrl("http://www.cnblogs.com/tinyphp/p/3858997.html");
        webView.setScrollListener(new ObservableWebView.WebViewScrollListener() {
            @Override
            public void onStartScroll(int scroolY) {
                Log.e("huang", "开始滑动了呀：onStartScroll:" + scrollY);
                handler.sendEmptyMessage(1);

            }

            @Override
            public void onStopScroll(int scrollY) {
                Log.e("huang", "停止滑动了呀：onStopScroll:" + scrollY);
                handler.sendEmptyMessage(0);

            }
        });

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://停止滑动
                    if (floatingView.getmViewState() == FloatingView.ViewStateEnum.CLOSE) {
                        floatingView.doAnimation();
                        floatingView.setmViewState(FloatingView.ViewStateEnum.OPEN);
                        floatingView.setImage(R.mipmap.pop_retract);
                    }
                    break;
                case 1://开始滑动
                    if (floatingView.getmViewState() == FloatingView.ViewStateEnum.OPEN) {
                        floatingView.doAnimation();
                        floatingView.setmViewState(FloatingView.ViewStateEnum.CLOSE);
                        floatingView.setImage(R.mipmap.pop_spread);
                    }
                    //测试test1添加一下东西
                    break;
                //测试一下冲突
            }
        }
    };
}
