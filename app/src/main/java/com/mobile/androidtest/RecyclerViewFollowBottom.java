package com.mobile.androidtest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * author：hj
 * time: 2018/3/26 0026 14:10
 * description:最后一个item始终显示在最底部的RecyclerView。
 * 1、如果不满一屏显示在屏幕最底部
 * 2、如果超过一屏显示在RecyclerView最下边
 */


public class RecyclerViewFollowBottom extends RecyclerView implements ViewTreeObserver.OnGlobalLayoutListener {
    private final Context context;

    public RecyclerViewFollowBottom(Context context) {
        this(context, null);
    }

    public RecyclerViewFollowBottom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewFollowBottom(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
        int computeVerticalScrollRange = computeVerticalScrollRange();
        Log.d("huang", "onGlobalLayout: computeVerticalScrollRange:" + computeVerticalScrollRange);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = wm.getDefaultDisplay().getHeight();

        int recyclerHeight = getHeight();
        Log.d("huang", "onGlobalLayout: recyclerHeight:" + recyclerHeight);

        int[] location = new int[2];
        getLocationOnScreen(location);

        if (location[1] + computeVerticalScrollRange < screenHeight) {
            int distance = screenHeight - (location[1] + computeVerticalScrollRange);
            //RecyclerView不满一屏
            View lastItemView = getChildAt(getChildCount() - 1);
            lastItemView.setPadding(0, distance, 0, 0);
        }

    }
}
