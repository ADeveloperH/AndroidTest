package com.mobile.androidtest;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author huangjian
 * @create 2018/8/29 0029
 * @Description
 */
public class OtherActivity extends Activity {
    private static final String TAG = "hj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDisplayMetricsInfo(this.getResources().getDisplayMetrics());
        TextView textView = new TextView(this);
        textView.setBackgroundColor(Color.RED);
        setContentView(textView, new LinearLayout.LayoutParams(dp2px(375), dp2px(375)));
    }

    private void getDisplayMetricsInfo(DisplayMetrics activityDisplayMetrics) {
        int widthPixels = activityDisplayMetrics.widthPixels;
        float density = activityDisplayMetrics.density;
        int densityDpi = activityDisplayMetrics.densityDpi;
        int heightPixels = activityDisplayMetrics.heightPixels;
        float scaledDensity = activityDisplayMetrics.scaledDensity;
        float xdpi = activityDisplayMetrics.xdpi;
        float ydpi = activityDisplayMetrics.ydpi;
        Log.d(TAG, "\n widthPixels:" + widthPixels
                + " \n heightPixels:" + heightPixels
                + " \n density:" + density
                + " \n densityDpi:" + densityDpi
                + " \n scaledDensity:" + scaledDensity
                + " \n xdpi:" + xdpi
                + " \n ydpi:" + ydpi
        );
    }

    private int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        int result = (int) (dpValue * scale + 0.5f);
        Log.d(TAG, "dp2px: result:" + result);
        return result;
    }

    private int dp2pxBySys(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        int result = (int) (dpValue * scale + 0.5f);
        Log.d(TAG, "dp2px: result:" + result);
        return result;
    }
}
