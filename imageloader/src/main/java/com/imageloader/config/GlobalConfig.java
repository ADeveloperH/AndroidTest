package com.imageloader.config;

import android.content.Context;
import android.os.Environment;

import com.imageloader.loader.GlideImageLoader;
import com.imageloader.loader.ILoader;

import java.io.File;

/**
 * author：hj
 * time: 2017/6/7 0007 11:14
 *
 * 图片加载相关全局配置信息
 * 可配置具体Loader.这里使用GlideLoader
 */


public class GlobalConfig {
    private GlobalConfig() {
    }

    public static ILoader getLoader() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        private static final ILoader INSTANCE = new GlideImageLoader();
    }

    /**
     * 获取磁盘缓存路径
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context) {
        File mCacheDir;
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState()
        )) {
            mCacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "glide");
        } else {
            mCacheDir = context.getCacheDir();// 如何获取系统内置的缓存存储路径
        }
        if (!mCacheDir.exists()) {
            mCacheDir.mkdirs();
        }
        return mCacheDir.getAbsolutePath();
    }
}
