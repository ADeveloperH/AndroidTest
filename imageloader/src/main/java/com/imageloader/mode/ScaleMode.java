package com.imageloader.mode;

/**
 * author：hj
 * time: 2017/6/8 0008 16:04
 * 对于任何图像操作，调整大小可能让图片失真
 * 提供两种显示图片的模式
 */


public enum ScaleMode {

    /**
     * 它会自适应ImageView的大小，并且会完整的显示图片在ImageView中，但是ImageView可能不会完全填充
     */
    FITCENTER,
    /**
     * 当图片比ImageView大的时候，他把超过ImageView的部分裁剪掉，尽可能的让ImageView 完全填充，
     * 但图像可能不会全部显示
     */
    CENTERCROP

}
