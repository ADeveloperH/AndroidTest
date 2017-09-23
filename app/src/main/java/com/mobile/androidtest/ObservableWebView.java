package com.mobile.androidtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author：hj
 * time: 2017/9/21 0021 21:21
 */


public class ObservableWebView extends WebView {

    private boolean isTouch;//是否触摸屏幕
    private boolean isStartScroll = false;//使用开始滑动的标示
    private int lastTop = -1;
    public ObservableWebView(final Context context) {
        super(context);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (scrollListener == null) {
            return;
        }

        if (!isSchedule) {
            isSchedule = true;
            if (mTimerTask == null) {
                mTimerTask = new MyTimerTask();
            }
            timer.schedule(mTimerTask, 0, 50);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                isTouch = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    public void setScrollListener(WebViewScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    /**
     * 监听WebView滑动状态
     */
    private WebViewScrollListener scrollListener;
    public interface WebViewScrollListener {
        //当前开始滑动了
        void onStartScroll(int scroolY);

        //当前已经停止滑动
        void onStopScroll(int scrollY);
    }



    private boolean isSchedule = false;
    private Timer timer = new Timer();
    private MyTimerTask mTimerTask;

    /**
     * 执行任务
     */
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.d("huang", "执行Task了");
            if (lastTop == getScrollY() && !isTouch) {
                //两次相等.且当前手指已经离开屏幕
                Log.d("huang", "停止滑动了");
                isSchedule = false;
                isStartScroll = false;
                if (mTimerTask != null) {
                    mTimerTask.cancel();  //将原任务从队列中移除
                    mTimerTask = null;
                }

                if (scrollListener != null) {
                    scrollListener.onStopScroll(getScrollY());
                }
            } else {
                if (!isStartScroll) {
                    isStartScroll = true;
                    if (scrollListener != null) {
                        scrollListener.onStartScroll(getScrollY());
                    }
                }
                Log.d("huang", "两次不相等，还在滑动");
                lastTop = getScrollY();
            }
        }
    }
}