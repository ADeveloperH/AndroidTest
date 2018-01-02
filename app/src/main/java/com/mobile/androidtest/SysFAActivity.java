package com.mobile.androidtest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/12/29 0029 14:42
 * description:
 */


public class SysFAActivity extends Activity {
    @BindView(R.id.imageView)
    ImageView imageView;
    private AnimationDrawable frameAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.load_anim, null);
        } else {
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.load_anim);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(frameAnim);
        } else {
            imageView.setBackgroundDrawable(frameAnim);
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_gc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (frameAnim != null && !frameAnim.isRunning()) {
                    frameAnim.start();
                }

                calculateSize();
                break;
            case R.id.btn_stop:
                if (frameAnim != null && frameAnim.isRunning()) {
                    frameAnim.stop();
                }
                break;
            case R.id.btn_gc:
                /**
                 * 释放系统动画的方法，原理，移除所有强引用frameAnim
                 * 方法一：
                 *   frameAnim = null;
                 ViewGroup parent = (ViewGroup) imageView.getParent();
                 parent.removeView(imageView);
                 imageView = null;
                 *
                 * 方法二：（不移除ImageView）
                 * frameAnim = null;
                 imageView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 *
                 */
                if (imageView != null) {
                    frameAnim = null;
                    imageView.setBackgroundResource(R.drawable.loaderror_1);
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getBackground();
                    Log.d( "huang", "onClick: " + drawable.getBitmap().getByteCount());
                }
                break;
        }
    }

    /**
     * 计算图片每张图片占用内存的大小
     */
    private void calculateSize() {
        int realDensityDpi = getResources().getDisplayMetrics().densityDpi;
        //xxhdpi下
//        int drawableDirDensityDpi = 480;
        //xxxhdpi下
        int drawableDirDensityDpi = 640;

        float scale = (float) realDensityDpi / drawableDirDensityDpi;
        double byteCount = (520 * scale + 0.5) * (400 * scale + 0.5) * 4;
        Log.d("huang", byteCount + "");
    }
}
