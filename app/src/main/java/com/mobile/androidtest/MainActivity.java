package com.mobile.androidtest;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.homenotifyview)
    HomeNormalNotifyView homenotifyview;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.iv_hand)
    ImageView ivHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ValueAnimator valueAnimator = ValueAnimator.ofInt(ivLine.getLayoutParams().height, 700);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("huang", "onAnimationUpdate: currentValue:" + currentValue);
                ivLine.getLayoutParams().height = currentValue;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivHand.getLayoutParams();

                layoutParams.bottomMargin = currentValue - 105;
                // 刷新视图，即重新绘制，从而实现动画效果
                ivLine.requestLayout();
                ivHand.requestLayout();
            }
        });
        valueAnimator.start();


        homenotifyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homenotifyview.hiddlenNotifyView();
            }
        });
    }

    @OnClick({R.id.btn_show, R.id.btn_hiddlen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                homenotifyview.showNotifyView();
                break;
            case R.id.btn_hiddlen:
                homenotifyview.hiddlenNotifyView();
                break;
        }
    }
}
