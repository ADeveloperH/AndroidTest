package com.mobile.androidtest.pagestate;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mobile.androidtest.frameanimation.MockFrameImageView;

/**
 * author：hj
 * time: 2017/11/3 0003 15:22
 */
public class PageStateLayout extends FrameLayout {
    private LayoutInflater layoutInflater;
    private View mContentView;

    private int retryClickViewId = -1;//重新获取按钮

    private int retryViewId;//失败页面的id
    private View mRetryView;

    private View retryAnimeView;//帧动画的View
    private int retryAnimeViewId;//失败页面动画的view

    private int retryInfoViewId;//失败页面重试文案
    private View retryInfoView;//失败页面重试文案的View

    private PageListener pageListener;//点击重试的事件

    public PageStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public PageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutInflater = LayoutInflater.from(context);
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


    /**
     * 显示失败重试页面
     *
     * @param msg
     */
    public void showRetry(CharSequence msg) {
        showRetry();

        if (retryInfoView == null) {
            if (retryInfoViewId > 0) {
                retryInfoView = mRetryView.findViewById(retryInfoViewId);
                if (retryInfoView instanceof TextView) {
                    ((TextView) retryInfoView).setText(msg);
                }
            }
        } else {
            if (retryInfoView instanceof TextView) {
                ((TextView) retryInfoView).setText(msg);
            }
        }
    }

    public void showRetry() {
        if (mRetryView == null && retryViewId > 0) {
            initRetryView();
        }
        showView(mRetryView);
    }


    /**
     * 初始化失败页面相关
     */
    private void initRetryView() {
        //初始化失败页面的View
        mRetryView = layoutInflater.inflate(retryViewId, this, false);
        setRetryView(mRetryView);
        //初始化做动画的view
        if (retryAnimeViewId > 0) {
            retryAnimeView = mRetryView.findViewById(retryAnimeViewId);
        }
        //初始化点击重试view事件
        if (retryClickViewId > 0) {
            View retryClickView = mRetryView.findViewById(retryClickViewId);
            if (retryClickView != null && pageListener != null) {
                retryClickView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pageListener.onRetry(v);
                    }
                });
            }
        }
    }


    /**
     * 显示具体content
     */
    public void showContent() {
        showView(mContentView);
    }

    private void showView(View view) {
        if (view == null) return;
        if (view == mContentView) {
            setVisibility(View.GONE);
            stopAnim(retryAnimeView);
            if (mContentView != null)
                mContentView.setVisibility(View.VISIBLE);
        } else {
            if (view == mRetryView) {
                setVisibility(View.VISIBLE);
                startAnim(retryAnimeView);
                if (mContentView != null)
                    mContentView.setVisibility(View.GONE);
                if (mRetryView != null)
                    mRetryView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setContentView(View view) {
        mContentView = view;
    }

    public void setRetryView(View view) {
        View retryView = mRetryView;
        removeView(retryView);
        addView(view);
        mRetryView = view;
    }

    public void setRetryViewId(int retryViewId) {
        this.retryViewId = retryViewId;
    }

    public void setRetryInfoViewId(int reryInfoViewId) {
        this.retryInfoViewId = reryInfoViewId;
    }

    public void setRetryClickViewId(int retryClickViewId) {
        this.retryClickViewId = retryClickViewId;
    }

    public void setRetryAnimeViewId(int retryAnimeViewId) {
        this.retryAnimeViewId = retryAnimeViewId;
        if (mRetryView != null && retryAnimeViewId > 0) {
            retryAnimeView = mRetryView.findViewById(retryAnimeViewId);
        }
    }

    public void setPageListener(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    /**
     * 开始帧动画
     */
    private void startAnim(View animeView) {
        if (null == animeView) {
            return;
        }
        if (animeView instanceof MockFrameImageView) {
            MockFrameImageView mockFrameImageView = (MockFrameImageView) animeView;
            Animatable animateDrawable = (Animatable) mockFrameImageView.getDrawable();
            if (animateDrawable != null && !animateDrawable.isRunning()) {
                animateDrawable.start();
            }
        } else {
            Drawable drawable = animeView.getBackground();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable frameAnim = (AnimationDrawable) drawable;
                if (frameAnim != null && !frameAnim.isRunning()) {
                    frameAnim.start();
                }
            }
        }

    }

    /**
     * 停止帧动画
     */
    private void stopAnim(View animeView) {
        if (null == animeView) {
            return;
        }
        if (animeView instanceof MockFrameImageView) {
            MockFrameImageView mockFrameImageView = (MockFrameImageView) animeView;
            Animatable animateDrawable = (Animatable) mockFrameImageView.getDrawable();
            if (animateDrawable != null && animateDrawable.isRunning()) {
                animateDrawable.stop();
            }
        } else {
            Drawable drawable = animeView.getBackground();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable frameAnim = (AnimationDrawable) drawable;
                if (frameAnim != null && frameAnim.isRunning()) {
                    frameAnim.stop();
                }
            }
        }
    }
}
