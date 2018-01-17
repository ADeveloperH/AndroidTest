package com.mobile.androidtest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

/**
 * author：hj
 * time: 2018/1/9 0009 14:58
 * description:
 */

public class HomeNormalNotifyView extends LinearLayout {
    public static final int SCROLL = 1;//循环跑马灯效果
    public static final int FLOAT_UP = 2;//向上浮动
    public static final int FLOAT_DOWN = 3;//向上浮动

    private ViewSwitcher viewSwitcher;
    private MyHandler mHandler;
    private long MARQUEE_TIME_SPAN = 1600;//viewSwitcher切换时间间隔
    private long MARQUEE_MOVE_TIME = 600;//viewSwitcher执行切换的时间
    private long FLOAT_TIME = 600;//上下浮动动画切换时间间隔
    private int float_distance;//浮动的幅度
    private Context context;

    public HomeNormalNotifyView(Context context) {
        this(context, null);
    }

    public HomeNormalNotifyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeNormalNotifyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        float_distance = dip2px(context, 8);
    }

    private boolean isAdded = false;

    /**
     * 动画显示当前布局
     * 属性动画相对自身移动需要API14+,这里用补间动画实现
     */
    public void showNotifyView() {
        setVisibility(View.VISIBLE);
        if (!isAdded) {
            View view = View.inflate(context, R.layout.viewswitcher, null);
            addView(view, LayoutParams.MATCH_PARENT, dip2px(context, 50));
            isAdded = true;
        }

        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        TranslateAnimation showAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);
        showAnim.setInterpolator(new LinearInterpolator());
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                delayStartMarqueeAnim();
                startFloatUpAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        startAnimation(showAnim);
    }


    /**
     * 隐藏当前view
     */
    public void hiddlenNotifyView() {
        TranslateAnimation hiddlenAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        hiddlenAnim.setDuration(1000);
        hiddlenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //执行完动画隐藏.移除动画，防止内存泄露
                clearAnimation();
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        startAnimation(hiddlenAnim);
    }


    /**
     * 执行向上浮动的动画
     */
    private void startFloatUpAnim() {
        ObjectAnimator floatUpAnim = ObjectAnimator.ofFloat(this,
                "translationY", 0, -float_distance);
        floatUpAnim.setInterpolator(new LinearInterpolator());
        floatUpAnim.setDuration(FLOAT_TIME);
        floatUpAnim.start();
        mHandler.removeMessages(FLOAT_DOWN);
        mHandler.sendEmptyMessageDelayed(FLOAT_DOWN, 1000);
    }

    /**
     * 执行向下浮动的动画
     */
    private void startFloatDownAnim() {
        ObjectAnimator floatDownAnim = ObjectAnimator.ofFloat(this,
                "translationY", -float_distance, 0);
        floatDownAnim.setInterpolator(new LinearInterpolator());
        floatDownAnim.setDuration(FLOAT_TIME);
        floatDownAnim.start();
        mHandler.removeMessages(FLOAT_UP);
        mHandler.sendEmptyMessageDelayed(FLOAT_UP, FLOAT_TIME);
    }

    /**
     * 执行文字箭头切换动画
     */
    private void delayStartMarqueeAnim() {
        Animation inAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inAnim.setDuration(MARQUEE_MOVE_TIME);
        inAnim.setInterpolator(new LinearInterpolator());
        Animation outAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        outAnim.setDuration(MARQUEE_MOVE_TIME);
        outAnim.setInterpolator(new LinearInterpolator());
        //设置View进入离开动画
        viewSwitcher.setInAnimation(inAnim);
        viewSwitcher.setOutAnimation(outAnim);

        if (mHandler == null) {
            mHandler = new MyHandler();
        }
        mHandler.removeMessages(SCROLL);
        mHandler.sendEmptyMessageDelayed(SCROLL, 400);
    }

    private class MyHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL://循环执行文字箭头切换
                    viewSwitcher.showNext();
                    mHandler.sendEmptyMessageDelayed(SCROLL, MARQUEE_TIME_SPAN);
                    break;
                case FLOAT_DOWN://执行下移动画
                    startFloatDownAnim();
                    break;
                case FLOAT_UP://执行上浮动画
                    startFloatUpAnim();
                    break;
            }

        }
    }

    public static int dip2px(Context context, int dip) {
        // dp和px的转换关系比例值
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
