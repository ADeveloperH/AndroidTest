package com.mobile.androidtest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/8/22.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {
    private String TAG = "huang";

    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canPullDown() {
//        Log.d(TAG, "canPullDown: getScrollY() == 0:" + (getScrollY() == 0));
        return getScrollY() == 0 && isCanPullDown;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(TAG, "onTouchEvent: PullableLinearLayout:" + getChildAt(0).getScrollY());
        return super.onTouchEvent(event);
    }

    private boolean isCanPullDown = true;

    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }
}
