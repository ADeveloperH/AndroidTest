package com.mobile.androidtest.pagestate;

import android.view.View;
import android.view.ViewGroup;

import com.mobile.androidtest.R;

/**
 * author：hj
 * time: 2017/11/6 0006 11:19
 *
 * Q1：为什么只提供一个contentView作为参数使用，而不是兼容Activity、Fragment？
 * 如果使用Activity就需要找到顶层的parent{@link android.view.Window.ID_ANDROID_CONTENT}，然后将状态页添加进去，但是
 * 这样会替换掉整个页面，包括ToolBar等，这样显然是不行的。
 * 如果使用Fragment，通过fragment.getView()获取Fragment显示的view作为contetView，这样也不好，因为contentView.getParent()
 * 获取的是ViewPager，这样也是不能达到只替换某个单独的Fragment的场景
 * 因此只使用contentView作为入参，完美适用所有场景
 *
 */

public class PageStateManager {

    private PageStateLayout pageStateLayout;

    private PageStateManager(Builder builder) {
        View contentView = builder.contentView;
        if (contentView == null) {
            throw new IllegalArgumentException("contentView cannot be null");
        }
        ViewGroup contentParent = (ViewGroup) contentView.getParent();
        if (contentParent == null) {
            throw new IllegalArgumentException("contentView must already has a parent ");
        }
        int childCount = contentParent.getChildCount();
        int contentIndex = 0;
        for (int i = 0; i < childCount; i++) {
            if (contentParent.getChildAt(i) == contentView) {
                contentIndex = i;
                break;
            }
        }

        //将状态页面添加到contentView所在的布局中
        pageStateLayout = new PageStateLayout(contentView.getContext());
        ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
        contentParent.addView(pageStateLayout, contentIndex + 1, contentViewLayoutParams);

        pageStateLayout.setRetryViewId(builder.retryViewId > 0 ? builder.retryViewId : R.layout.loaderror_layout2);
        pageStateLayout.setRetryClickViewId(builder.retryClickViewId > 0 ? builder.retryClickViewId : R.id.reryClickView);
        pageStateLayout.setRetryInfoViewId(builder.retryInfoViewId > 0 ? builder.retryInfoViewId : R.id.reryInfoView);
        pageStateLayout.setRetryAnimeViewId(builder.retryAnimeViewId > 0 ? builder.retryAnimeViewId : R.id.retryAnimeView);
        pageStateLayout.setContentView(contentView);
    }

    public static class Builder {
        private View contentView;//要显示的内容View
        private int retryViewId;
        private int retryClickViewId;
        private int retryInfoViewId;
        private int retryAnimeViewId;

        public Builder(View contentView) {
            this.contentView = contentView;
        }

        /**
         * 设置失败布局View的Id
         *
         * @param retryViewId
         * @return
         */
        public Builder setRetryViewId(int retryViewId) {
            if (retryViewId > 0) {
                this.retryViewId = retryViewId;
            }
            return this;
        }

        /**
         * 设置失败后可点击重试的View的Id
         *
         * @param retryClickViewId
         * @return
         */
        public Builder setRetryClickViewId(int retryClickViewId) {
            if (retryClickViewId > 0) {
                this.retryClickViewId = retryClickViewId;
            }
            return this;
        }

        /**
         * 设置失败后显示文案的View的Id
         *
         * @param retryInfoViewId
         * @return
         */
        public Builder setRetryInfoViewId(int retryInfoViewId) {
            if (retryInfoViewId > 0) {
                this.retryInfoViewId = retryInfoViewId;
            }
            return this;
        }

        /**
         * 设置失败后做动画的View的Id
         *
         * @param retryAnimeViewId
         * @return
         */
        public Builder setRetryAnimeViewId(int retryAnimeViewId) {
            if (retryAnimeViewId > 0) {
                this.retryAnimeViewId = retryAnimeViewId;
            }
            return this;
        }

        public PageStateManager build() {
            return new PageStateManager(this);
        }
    }


    public void setPageListener(final PageListener pageListener) {
        if (pageStateLayout != null) {
            pageStateLayout.setPageListener(pageListener);
        }
    }

    public void showContent() {
        if (pageStateLayout != null) {
            pageStateLayout.showContent();
//            contentParent.removeView(pageStateLayout);
        }
    }

    public void showRetryView() {
        if (pageStateLayout != null) {
            pageStateLayout.showRetry();
        }
    }

    public void showRetryView(CharSequence msg) {
        if (pageStateLayout != null) {
            pageStateLayout.showRetry(msg);
        }
    }
}
