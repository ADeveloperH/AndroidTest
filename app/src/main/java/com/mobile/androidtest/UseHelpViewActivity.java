package com.mobile.androidtest;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 *
 */
public class UseHelpViewActivity extends Activity {

    private LinearLayout llRootView;
    private Context context;
    private ImageView ivHand;
    private ImageView ivLine;
    public static final int DISMISS_ANIM = 11;
    public static final int SLIP_ANIM = 22;
    public static final int SLIP_ANIM_TIME = 700;//滑动动画时间
    public static final int DISMISS_ANIM_TIME = 300;//消失动画时间
    public static final int PAUSE_TIME = 1000;//动画间隔时间
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | localLayoutParams.flags);
        }
        setContentView(R.layout.home_usehelpview);

        context = this;
        llRootView = (LinearLayout) findViewById(R.id.rl_rootview);
        ivHand = (ImageView) findViewById(R.id.iv_hand);
        ivLine = (ImageView) findViewById(R.id.iv_line);

        initData();
    }

    private void initData() {
        llRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showUseHelpView();
            }
        }, 1000);
        llRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });
    }

    /**
     * 显示新手引导蒙版
     */
    private void showUseHelpView() {
        // 获得每次变化后的属性值
        startSlipAnim();
        mHandler = new MyHandler();
        mHandler.sendEmptyMessageDelayed(DISMISS_ANIM, SLIP_ANIM_TIME);
    }


    /**
     * 滑动动画
     */
    private void startSlipAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(dip2px(this, 66),
                dip2px(this, 195));
        valueAnimator.setDuration(SLIP_ANIM_TIME);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (int) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                RelativeLayout.LayoutParams lineLayoutParams = (RelativeLayout.LayoutParams) ivLine.getLayoutParams();
                lineLayoutParams.height = currentValue;
                ivLine.requestLayout();
                // 刷新视图，即重新绘制，从而实现动画效果
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivHand.getLayoutParams();
                layoutParams.bottomMargin = currentValue - dip2px(context, 64);
                ivHand.requestLayout();
                ivLine.setVisibility(View.VISIBLE);
                ivHand.setVisibility(View.VISIBLE);
            }
        });
        valueAnimator.start();
    }


    /**
     * 执行消失的动画
     */
    private void startDismissAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(DISMISS_ANIM_TIME);
        alphaAnimation.setFillAfter(false);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        ivHand.setAnimation(alphaAnimation);
        ivLine.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivLine.setVisibility(View.GONE);
                ivHand.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.start();
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DISMISS_ANIM:
                    startDismissAnim();
                    sendEmptyMessageDelayed(SLIP_ANIM, DISMISS_ANIM_TIME + PAUSE_TIME);
                    break;
                case SLIP_ANIM:
                    startSlipAnim();
                    sendEmptyMessageDelayed(DISMISS_ANIM, SLIP_ANIM_TIME);
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(SLIP_ANIM);
        mHandler.removeMessages(DISMISS_ANIM);
        mHandler.removeCallbacksAndMessages(null);
    }

    public static int dip2px(Context context, int dip) {
        // dp和px的转换关系比例值
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
