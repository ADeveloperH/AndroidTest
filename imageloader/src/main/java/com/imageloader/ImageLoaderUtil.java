package com.imageloader;

import android.content.Context;
import android.view.View;

import com.imageloader.config.GlobalConfig;
import com.imageloader.config.ImageLoderConfig;

/**
 * author：hj
 * time: 2017/6/7 0007 09:19
 * 图片加载工具类
 */
public class ImageLoaderUtil {

    public static ImageLoderConfig.ConfigBuilder with(Context context) {
        return new ImageLoderConfig.ConfigBuilder(context);
    }


    public static void clearMomoryCache(View view) {
        GlobalConfig.getLoader().clearMomoryCache(view);
    }

    //清理内存缓存 在UI主线程中进行
    public static void clearMomory(Context context) {
        GlobalConfig.getLoader().clearMomory(context);
    }

    //清理磁盘缓存 需要在子线程中执行
    public static void clearDiskCache(Context context) {
        GlobalConfig.getLoader().clearDiskCache(context);
    }

    public static void clearAllMemoryCaches(Context context) {
        clearDiskCache(context);
        clearMomory(context);
    }

//    /**
//     * 设置磁盘缓存
//     *
//     * @param diskCachePath 磁盘缓存的路径
//     * @param diskCacheName 磁盘缓存的名称
//     * @param diskCacheSize 磁盘缓存的大小。单位为MB
//     */
//    public static void setDiskCache(Context context, String diskCachePath,
//                                    String diskCacheName, int diskCacheSize) {
//        GlobalConfig.getLoader().setDiskCache(context, diskCachePath,
//                diskCacheName, diskCacheSize);
//    }
}
