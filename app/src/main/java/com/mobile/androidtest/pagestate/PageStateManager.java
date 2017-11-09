package com.mobile.androidtest.pagestate;

import android.view.View;
import android.view.ViewGroup;

import com.mobile.androidtest.R;

/**
 * author：hj
 * time: 2017/11/6 0006 11:19
 */

public class PageStateManager {
    private ViewGroup contentParent;
    private int RETRY_LAYOUT_ID = R.layout.loaderror_layout2;
    private PageStateLayout pageStateLayout;
    public PageStateManager(View contentView) {
        if (contentView == null) {
            throw new IllegalArgumentException("contentView cannot be null");
        }
        contentParent = (ViewGroup) contentView.getParent();
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

        pageStateLayout.setRetryViewId(RETRY_LAYOUT_ID);
        pageStateLayout.setRetryClickViewId(R.id.reryClickView);
        pageStateLayout.setRetryAnimeView(R.id.retryAnimeView);
        pageStateLayout.setContentView(contentView);
    }

    public void setPageListener(final PageListener pageListener) {
        if (pageStateLayout != null) {
            pageStateLayout.setPageListener(pageListener);
        }
    }

    public void showContent() {
        if (pageStateLayout != null) {
            pageStateLayout.showContent();
            contentParent.removeView(pageStateLayout);
        }
    }

    public void showRetryView() {
        if (pageStateLayout != null) {
            pageStateLayout.showRetry();
        }
    }

    public void showRetryView(String msg) {
        if (pageStateLayout != null) {
            pageStateLayout.showRetry(msg);
        }
    }
}
