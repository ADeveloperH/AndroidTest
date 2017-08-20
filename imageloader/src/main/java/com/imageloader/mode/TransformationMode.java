package com.imageloader.mode;

/**
 * author：hj
 * time: 2017/6/12 0012 16:38
 * bitmapTransform类型
 */


public enum TransformationMode {
    //圆（带边框）
    CropCircleTransformation,
    //圆角矩形
    RoundedCornersTransformation,
    //颜色滤镜
    ColorFilterTransformation,
    //灰色蒙版
    GrayscaleTransformation;
}
