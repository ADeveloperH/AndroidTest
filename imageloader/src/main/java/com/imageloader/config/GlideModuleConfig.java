package com.imageloader.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * author：hj
 * time: 2017/6/13 0013 11:10
 * 自定义GlideModule
 * 设置缓存路径缓存大小等相关信息
 */

public class GlideModuleConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        builder.setDiskCache(new DiskLruCacheFactory(GlobalConfig.getDiskCachePath(context), "cache", diskCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
