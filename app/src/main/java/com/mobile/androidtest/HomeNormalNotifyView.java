package com.mobile.androidtest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private ViewSwitcher viewSwitcher;
    private MarqueeHandler marqueeHandler;
    private long MARQUEE_TIME_SPAN = 2000;//跑马灯切换时间间隔
    private long FLOAT_TIME_SPAN = 1000;//上下浮动动画切换时间间隔

    private static final int DEFAULT_IN_ANIM_ID = R.anim.home_notify_in;
    private static final int DEFAULT_OUT_ANIM_ID = R.anim.home_notify_out;
    private static final int DEFAULT_INTERPOLATOR = android.R.interpolator.linear;
    private Context context;
    private ObjectAnimator repeatFloatAnim;

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
    }

    /**
     * 动画显示当前布局
     * 属性动画相对自身移动需要API14+,这里用补间动画实现
     */
    public void showNotifyView() {
        setVisibility(View.VISIBLE);
        View view = View.inflate(context, R.layout.viewswitcher, null);
        addView(view, LayoutParams.MATCH_PARENT, dip2px(context, 50));

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
                startFloatAnim();
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
        hiddlenAnim.setDuration(300);
        hiddlenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //执行完动画隐藏.移除动画，防止内存泄露
                repeatFloatAnim.cancel();
                repeatFloatAnim = null;
                clearAnimation();
                marqueeHandler.removeCallbacksAndMessages(null);
                marqueeHandler = null;
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        startAnimation(hiddlenAnim);
    }


    /**
     * 执行上下浮动的动画
     */
    private void startFloatAnim() {
        repeatFloatAnim = ObjectAnimator.ofFloat(this,
                "translationY",
                0, -23.0f, 0, 23.0f, 0);
        repeatFloatAnim.setInterpolator(new LinearInterpolator());
        repeatFloatAnim.setDuration(FLOAT_TIME_SPAN);
        repeatFloatAnim.setRepeatCount(ValueAnimator.INFINITE);
        repeatFloatAnim.setRepeatMode(ValueAnimator.RESTART);
        repeatFloatAnim.start();
        startMarqueeAnim();
    }

    /**
     * 执行跑马灯切换动画
     */
    private void startMarqueeAnim() {
        Animation inAnim = AnimationUtils.loadAnimation(context, DEFAULT_IN_ANIM_ID);
        Animation outAnim = AnimationUtils.loadAnimation(context, DEFAULT_OUT_ANIM_ID);
        inAnim.setInterpolator(context, DEFAULT_INTERPOLATOR);
        outAnim.setInterpolator(context, DEFAULT_INTERPOLATOR);
        //设置View进入离开动画
        viewSwitcher.setInAnimation(inAnim);
        viewSwitcher.setOutAnimation(outAnim);

        if (marqueeHandler == null) {
            marqueeHandler = new MarqueeHandler();
        }
        marqueeHandler.removeMessages(SCROLL);
        marqueeHandler.sendEmptyMessageDelayed(SCROLL, MARQUEE_TIME_SPAN);
    }

    private class MarqueeHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL://
                    viewSwitcher.showNext();
                    marqueeHandler.sendEmptyMessageDelayed(SCROLL, MARQUEE_TIME_SPAN);
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
