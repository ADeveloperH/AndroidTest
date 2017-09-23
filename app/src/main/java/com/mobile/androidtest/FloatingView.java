package com.mobile.androidtest;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatingView extends LinearLayout implements View.OnClickListener {

    private static final int ANIMATION_TIME = 500;
    private static final int SCALE_ANIMATION_TIME = 100;
    private ImageView mIconImageView;
    private View mView;
    private int mWidth;
    private int mHeight;
    private ViewStateEnum mViewState;
    private ValueAnimator mTranslateAnimator;
    private ValueAnimator mScaleAnimator;
    private TextView comment;
    private TextView hot;
    private LinearLayout iconLayout, contentLayout;
    private float x1, x2;

    public FloatingView(Context context) {
        this(context, null);
    }

    public FloatingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = LayoutInflater.from(context).inflate(R.layout.popwindow_heduohao, null);
        addView(mView);
        initView();
        mViewState = ViewStateEnum.OPEN;
    }

    private void initView() {
        mIconImageView = (ImageView) mView.findViewById(R.id.icon);
        iconLayout = (LinearLayout) mView.findViewById(R.id.icon_layout);
        contentLayout = (LinearLayout) mView.findViewById(R.id.content_layout);
        comment = (TextView) mView.findViewById(R.id.comment);
        hot = (TextView) mView.findViewById(R.id.hot);
        iconLayout.setOnClickListener(this);
        contentLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (x2 - x1 > 50) {
                            doAnimation();
                            mViewState = ViewStateEnum.CLOSE;
                            mIconImageView.setImageDrawable(getResources().getDrawable(R.mipmap.pop_spread));
                        }
                        break;
                }
                return true;

            }
        });
    }

    public void setCenterText(String comments, String hots) {
        comment.setText(comments);
        hot.setText(hots);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_layout:
                if (mViewState == ViewStateEnum.OPEN) {
                    doAnimation();
                    mViewState = ViewStateEnum.CLOSE;
                    mIconImageView.setImageDrawable(getResources().getDrawable(R.mipmap.pop_spread));
                } else {
                    doAnimation();
                    mViewState = ViewStateEnum.OPEN;
                    mIconImageView.setImageDrawable(getResources().getDrawable(R.mipmap.pop_retract));
                }
                break;
            default:
                break;
        }
    }

    public void doAnimation() {
        initTranslateAnimation();
        initScaleAnimation();
        AnimatorSet set = new AnimatorSet();
        set.play(mTranslateAnimator).with(mScaleAnimator);
        set.start();
    }

    private void initTranslateAnimation() {
        int closeDistance = mWidth - iconLayout.getWidth() - mView.getPaddingLeft() * 2;
        mTranslateAnimator = ValueAnimator.ofInt(0, closeDistance);
        if (mViewState == ViewStateEnum.CLOSE) {
            mTranslateAnimator.setEvaluator(new TypeEvaluator<Integer>() {
                @Override
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return (int) (endValue - fraction * (endValue - startValue));
                }
            });
        }
        mTranslateAnimator.setDuration(ANIMATION_TIME);
        mTranslateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mView.layout(value, 0, mWidth, mHeight);
            }
        });
    }

    private void initScaleAnimation() {
        mScaleAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        mScaleAnimator.setDuration(SCALE_ANIMATION_TIME);
        if (mViewState == ViewStateEnum.CLOSE) {
            mScaleAnimator.setEvaluator(new TypeEvaluator<Float>() {
                @Override
                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    return endValue - fraction * (endValue - startValue);
                }
            });
        }
        mScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = mView.getMeasuredWidth();
        int viewHeight = mView.getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, viewWidth,
                    getResources().getDisplayMetrics());
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, viewHeight,
                    getResources().getDisplayMetrics());
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    public enum ViewStateEnum {
        OPEN, CLOSE
    }

    public ViewStateEnum getmViewState(){
        return mViewState;
    }

    public void setmViewState(ViewStateEnum state){
        mViewState = state;
    }

    public void setImage(int resId){
        mIconImageView.setImageDrawable(getResources().getDrawable(resId));
    }
}
