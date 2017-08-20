package com.imageloader.mode;

/**
 * author：hj
 * time: 2017/6/7 0007 15:20
 * 缓存加载策略（硬盘）
 */


public enum DiskCacheMode {
    /**
     * 缓存源资源和转换后的资源
     */
    ALL,
    /**
     * 不作任何磁盘缓存
     */
    NONE,
    /**
     * 缓存源资源
     */
    SOURCE,
    /**
     * 缓存转换后的资源
     */
    RESULT;
}
