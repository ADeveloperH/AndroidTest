package com.mobile.androidtest;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mobile.androidtest.frameanimation.FrameAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/12/29 0029 14:42
 * description:
 */


@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
public class CusFAActivity extends Activity implements FrameAnimation.FrameAnimationCallBack {
    @BindView(R.id.imageView)
    ImageView imageView;
    private FrameAnimation frameAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus);
        ButterKnife.bind(this);

        frameAnimation = new FrameAnimation(this, imageView, Ress.LOAD_FRAME_RESS)
                .setDuration(50)
                .setFrameAnimationCallBack(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (frameAnimation != null) {
                    frameAnimation.start();
                }
                break;
            case R.id.btn_stop:
                if (frameAnimation != null) {
                    frameAnimation.stop();
                    frameAnimation.clearCache();
                }
                break;
            case R.id.btn_reset:
                if (frameAnimation != null) {
                    frameAnimation.reset();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        frameAnimation.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        frameAnimation.clearCache();
        super.onDestroy();
    }

    @Override
    public void onFrameAnimationStart(FrameAnimation frameAnimation) {
        if (this.frameAnimation.equals(frameAnimation)) {
            Log.e("huang", "onFrameAnimationStart");
        }
    }

    @Override
    public void onFrameAnimationEnd(FrameAnimation frameAnimation) {
        if (this.frameAnimation.equals(frameAnimation)) {
            Log.e("huang", "onFrameAnimationEnd");
        }
    }
}
