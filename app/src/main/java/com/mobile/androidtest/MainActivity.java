package com.mobile.androidtest;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hj";
    private LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button1====================================================");
                Toast toast = Toast.makeText(MainActivity.this, "测试意思啊啊1", Toast.LENGTH_LONG);
                TextView textView = new TextView(MainActivity.this);
                textView.setText("测试一下1");
                textView.setBackgroundColor(Color.BLACK);
                textView.setLayoutParams(new ViewGroup.LayoutParams(dp2px(100),dp2px(20)));
                toast.setView(textView);
                toast.show();
                clickBtn();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button2====================================================");
                startAdaptive();
                llRoot.removeViewAt(llRoot.getChildCount() - 1);
                addTextView();

                Toast toast = Toast.makeText(MainActivity.this, "测试意思啊啊2", Toast.LENGTH_LONG);
                TextView textView = new TextView(MainActivity.this);
                textView.setBackgroundColor(Color.BLACK);
                textView.setText("测试一下2");
                textView.setLayoutParams(new ViewGroup.LayoutParams(dp2px(100),dp2px(20)));
                toast.setView(textView);
                toast.show();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button3====================================================");
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button4====================================================");
                cancelAdapt();
            }
        });

        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        addTextView();

    }

    private void addTextView() {
        TextView textView = new TextView(this);
        textView.setBackgroundColor(Color.RED);
        llRoot.addView(textView, new LinearLayout.LayoutParams(dp2px(375), dp2px(375)));
    }

    /**
     * 开始适配
     * https://github.com/Blankj/AndroidUtilCode/issues/597
     *
     * https://github.com/JessYanCoding/AndroidAutoSize
     */
    private void startAdaptive() {
        //系统的不会改变
        DisplayMetrics sysDM = Resources.getSystem().getDisplayMetrics();

        DisplayMetrics activityDM = getResources().getDisplayMetrics();
        DisplayMetrics applicationDM = getApplication().getResources().getDisplayMetrics();
        activityDM.density = activityDM.widthPixels / 375f;
        activityDM.densityDpi = (int) (160 * activityDM.density);
        activityDM.scaledDensity = activityDM.density * (sysDM.scaledDensity / sysDM.density);

        applicationDM.density = activityDM.density;
        applicationDM.densityDpi = activityDM.densityDpi;
        applicationDM.scaledDensity = activityDM.scaledDensity;
    }


    /**
     * 取消适配
     */
    private void cancelAdapt() {
        DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        DisplayMetrics appDm = getApplication().getResources().getDisplayMetrics();
        DisplayMetrics activityDm = getResources().getDisplayMetrics();
        activityDm.density = systemDm.density;
        activityDm.scaledDensity = systemDm.scaledDensity;
        activityDm.densityDpi = systemDm.densityDpi;

        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi = systemDm.densityDpi;
    }

    private void clickBtn() {
        DisplayMetrics activityDisplayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG, "==========================activityDisplayMetrics ");
        getDisplayMetricsInfo(activityDisplayMetrics);
        Log.d(TAG, "==========================systemDisplayMetrics ");
        DisplayMetrics systemDisplayMetrics = Resources.getSystem().getDisplayMetrics();
        getDisplayMetricsInfo(systemDisplayMetrics);
        Log.d(TAG, "==========================applicationDisplayMetrics ");
        DisplayMetrics applicationDisplayMetrics = getApplication().getResources().getDisplayMetrics();
        getDisplayMetricsInfo(applicationDisplayMetrics);
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
}
