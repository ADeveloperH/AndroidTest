package com.imageloader.callback;

import android.graphics.drawable.Drawable;

/**
 * author：hj
 * time: 2017/6/7 0007 15:06
 */

public interface ImageLoadeListener {

    //加载完成
    void onLoadCompleted(Drawable drawable);

    //失败
    void onLoadFailed(Throwable t);
}
