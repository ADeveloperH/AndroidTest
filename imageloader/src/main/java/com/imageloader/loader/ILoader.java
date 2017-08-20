package com.imageloader.loader;

import android.content.Context;
import android.view.View;

import com.imageloader.config.ImageLoderConfig;

/**
 * author：hj
 * time: 2017/6/7 0007 09:20
 */


public interface ILoader {
    //加载图片
    void display(ImageLoderConfig imageLoderConfig);

    //清除内存缓存
    void clearMomoryCache(View view);

    //清除内存缓存
    void clearMomory(Context context);

    //清除磁盘缓存
    void clearDiskCache(Context context);

    //清除所有的缓存
    void clearAllMemoryCaches(Context context);

//    //设置磁盘缓存路径和大小（全局）
//    void setDiskCache(Context context,String diskCachePath,String diskCacheName,int diskCacheSize);
}
