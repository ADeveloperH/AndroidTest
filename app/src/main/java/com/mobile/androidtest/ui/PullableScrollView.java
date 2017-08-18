package com.mobile.androidtest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable {

    private String TAG = "huang";

    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        Log.d(TAG, "canPullDown: getScrollY() == 0:" + (getScrollY() == 0));
        return getScrollY() == 0;
    }
}
