package com.mobile.androidtest.ui;

/**
 * author：hj
 * time: 2017/8/20 0020 17:42
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

/**
 * author：hj
 * time: 2016/10/31 0031 10:50
 *
 *
 */

public class ProgressWebView extends RelativeLayout{
    private WebView mWebView;
    public ProgressWebView(Context context) {
        super(context);
        initView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mWebView = new WebView(context);
        mWebView.setOverScrollMode(OVER_SCROLL_NEVER);
        addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        initDefaultSetting();
    }


    /**
     * 使用默认设置
     */
    private void initDefaultSetting() {
        initWebViewSetting();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initWebViewSetting() {
        WebSettings webviewSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 21) {
            //设置允许混合加载(http/https)
            webviewSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webviewSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setDatabaseEnabled(true);
        // 设置响应js
        webviewSettings.setJavaScriptEnabled(true);
        //自适应屏幕
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        //支持缩放
        webviewSettings.setSupportZoom(true);
        webviewSettings.setBuiltInZoomControls(true);
        //支持通过JS打开新窗口
        //获取useragent
        String userAgent = webviewSettings.getUserAgentString();
//        mWebView.setBackgroundColor(R.color.transparent);

        mWebView.setFocusable(true);
        mWebView.requestFocus();
        mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

    public void setClickable(boolean value) {
        mWebView.setClickable(value);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void destory() {
        if (mWebView != null) {
            this.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public void setWebViewClient(WebViewClient value) {
        mWebView.setWebViewClient(value);
    }

    public void loadUrl(String loadUrl) {
        if (TextUtils.isEmpty(loadUrl)) {
            return;
        }
        mWebView.loadUrl(loadUrl);
    }
}
