package com.mobile.androidtest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobile.androidtest.ui.MyPullToRefreshListener;
import com.mobile.androidtest.ui.ProgressWebView;
import com.mobile.androidtest.ui.PullToRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    ProgressWebView webView;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    private String TAG = "huang";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        webView.loadUrl("http://www.jianshu.com/p/d21989bea448");
        mWebView = webView.getWebView();
        webView.setWebViewClient(new WebViewClient() {
            //重写此方法，浏览器内部跳转
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


            public void onPageFinished(WebView view, String url) {
                loadFinish();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
        });

        refreshView.setOnRefreshListener(new MyPullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                webView.getWebView().reload();
            }
        });
    }


    private void loadFinish() {
        if (refreshView.isRefreshing()) {
            refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    }
}
