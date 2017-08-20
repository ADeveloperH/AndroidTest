package com.imageloader.config;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.imageloader.callback.ImageLoadeListener;
import com.imageloader.mode.AnimationMode;
import com.imageloader.mode.DiskCacheMode;
import com.imageloader.mode.PriorityMode;
import com.imageloader.mode.ScaleMode;
import com.imageloader.mode.TransformationMode;

import java.io.File;

/**
 * author：hj
 * time: 2017/6/7 0007 09:19
 * <p>
 * 图片加载设置加载行为的配置类
 */

public class ImageLoderConfig {
    private Context context;

    private String loadStr;//加载图片的参数（可以为url、filePath等）
    private File loadFile;//加载图片的参数（为file类型）
    private int loadResId;//加载图片的参数（为资源id）

    private int placeholderResId;//加载中占位符的图片id
    private int errorResId;//加载失败的图片id

    private boolean isSkipMemoryCache;//是否跳过内存缓存
    private DiskCacheMode diskCacheMode;//设置缓存策略

    private ImageLoadeListener loadeListener;
    private PriorityMode priorityMode;//图片加载的优先级
    private ScaleMode scaleMode;//图片缩放的模式

    private boolean dontAnimate = true;//是否取消加载动画
    private AnimationMode animationType; //动画资源Type
    private int animationId; //动画资源id
    private Animation animation; //动画资源
    private ViewPropertyAnimation.Animator animator; //动画资源

    private boolean isGif = false;//是否是Gif
    private boolean asBitmap;//加载Gif时是否只显示Gif第一帧

    private TransformationMode transformationMode;
    private int borderWidth;
    private int borderColor;
    private int transformColor;
    private int roundedCornersRadius;
    private int roundedCornersMargin;

    private View targetView;//加载到目标View

    private int oWidth;//需要修改的图片宽度
    private int oHeight;//需要修改的图片高度

    public ImageLoderConfig(ConfigBuilder configBuilder) {
        this.context = configBuilder.context;
        this.loadStr = configBuilder.loadStr;
        this.loadFile = configBuilder.loadFile;
        this.loadResId = configBuilder.loadResId;
        this.targetView = configBuilder.targetView;
        this.loadeListener = configBuilder.loadeListener;
        this.placeholderResId = configBuilder.placeholderResId;
        this.errorResId = configBuilder.errorResId;
        this.isSkipMemoryCache = configBuilder.isSkipMemoryCache;
        this.diskCacheMode = configBuilder.diskCacheMode;
        this.priorityMode = configBuilder.priorityMode;
        this.scaleMode = configBuilder.scaleMode;
        this.oWidth = configBuilder.oWidth;
        this.oHeight = configBuilder.oHeight;
        this.dontAnimate = configBuilder.dontAnimate;
        this.animationId = configBuilder.animationId;
        this.animationType = configBuilder.animationType;
        this.animator = configBuilder.animator;
        this.animation = configBuilder.animation;
        this.isGif = configBuilder.isGif;
        this.asBitmap = configBuilder.asBitmap;
        this.transformationMode = configBuilder.transformationMode;
        this.borderWidth = configBuilder.borderWidth;
        this.borderColor = configBuilder.borderColor;
        this.transformColor = configBuilder.transformColor;
        this.roundedCornersRadius = configBuilder.roundedCornersRadius;
        this.roundedCornersMargin = configBuilder.roundedCornersMargin;

    }

    //加载图片
    private void display() {
        GlobalConfig.getLoader().display(this);
    }

    public Context getContext() {
        return context;
    }

    public String getLoadStr() {
        return loadStr;
    }

    public File getLoadFile() {
        return loadFile;
    }

    public int getLoadResId() {
        return loadResId;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public DiskCacheMode getDiskCacheMode() {
        return diskCacheMode;
    }

    public ImageLoadeListener getLoadeListener() {
        return loadeListener;
    }

    public PriorityMode getPriorityMode() {
        return priorityMode;
    }

    public ScaleMode getScaleMode() {
        return scaleMode;
    }

    public boolean isDontAnimate() {
        return dontAnimate;
    }

    public AnimationMode getAnimationType() {
        return animationType;
    }

    public int getAnimationId() {
        return animationId;
    }

    public Animation getAnimation() {
        return animation;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public View getTargetView() {
        return targetView;
    }

    public int getoWidth() {
        return oWidth;
    }

    public int getoHeight() {
        return oHeight;
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public TransformationMode getTransformationMode() {
        return transformationMode;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public int getTransformColor() {
        return transformColor;
    }

    public int getRoundedCornersRadius() {
        return roundedCornersRadius;
    }

    public int getRoundedCornersMargin() {
        return roundedCornersMargin;
    }

    public static class ConfigBuilder {
        private Context context;
        private String loadStr;//加载图片的参数（可以为url、filePath等）
        private File loadFile;//加载图片的参数（为file类型）
        private int loadResId;//加载图片的参数（为资源id）

        private View targetView;//加载到目标View
        private ImageLoadeListener loadeListener;
        private int placeholderResId;//加载中占位符的图片id
        private int errorResId;//加载失败的图片id
        private boolean isSkipMemoryCache;//是否跳过内存缓存
        private DiskCacheMode diskCacheMode;//设置缓存策略
        private PriorityMode priorityMode;//图片加载的优先级
        private ScaleMode scaleMode;//图片缩放的模式
        private boolean dontAnimate = true;//是否取消加载动画
        private AnimationMode animationType; //动画资源Type
        private int animationId; //动画资源id
        private Animation animation; //动画资源
        private boolean isGif = false;//是否是Gif
        private boolean asBitmap;//加载Gif时是否只显示Gif第一帧
        private ViewPropertyAnimation.Animator animator; //动画资源

        private TransformationMode transformationMode;
        private int borderWidth;
        private int borderColor;
        private int transformColor;
        private int roundedCornersRadius;
        private int roundedCornersMargin;

        private int oWidth;//需要修改的图片宽度
        private int oHeight;//需要修改的图片高度

        public ConfigBuilder(Context context) {
            this.context = context;
        }

        public ConfigBuilder load(String loadStr) {
            this.loadStr = loadStr;
            if (loadStr.contains(".gif")) {
                isGif = true;
            }
            return this;
        }

        public ConfigBuilder load(File file) {
            this.loadFile = file;
            if (file.getAbsolutePath().contains(".gif")) {
                isGif = true;
            }
            return this;
        }

        public ConfigBuilder load(int resId) {
            this.loadResId = resId;
            return this;
        }

        /**
         * 以resId未判断是否是Gif资源。这里可以手动设置以Gif资源加载
         *
         * @return
         */
        public ConfigBuilder asGif() {
            this.isGif = true;
            return this;
        }

        public ConfigBuilder asBitmap() {
            this.asBitmap = true;
            return this;
        }

        /**
         * 圆圈
         * @return
         */
        public ConfigBuilder asCropCircle() {
            this.transformationMode = TransformationMode.CropCircleTransformation;
            return this;
        }

        /**
         * 圆圈设置边框
         * @return
         */
        public ConfigBuilder asCropCircle(int borderWidth, int borderColor) {
            this.transformationMode = TransformationMode.CropCircleTransformation;
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
            return this;
        }

        /**
         * 灰色滤镜
         * @return
         */
        public ConfigBuilder asGrayscale() {
            this.transformationMode = TransformationMode.GrayscaleTransformation;
            return this;
        }

        /**
         * 颜色滤镜
         * @param transformColor
         * @return
         */
        public ConfigBuilder asColorFilter(int transformColor) {
            this.transformationMode = TransformationMode.ColorFilterTransformation;
            this.transformColor = transformColor;
            return this;
        }

        /**
         * 圆角矩形
         * @param roundedCornersRadius
         * @param roundedCornersMargin
         * @return
         */
        public ConfigBuilder asRoundedCorners(int roundedCornersRadius, int roundedCornersMargin) {
            this.transformationMode = TransformationMode.RoundedCornersTransformation;
            this.roundedCornersRadius = roundedCornersRadius;
            this.roundedCornersMargin = roundedCornersMargin;
            return this;
        }

        public ConfigBuilder placeholder(int placeholder) {
            this.placeholderResId = placeholder;
            return this;
        }

        public ConfigBuilder error(int error) {
            this.errorResId = error;
            return this;
        }

        public ConfigBuilder dontAnimate() {
            this.dontAnimate = true;
            return this;
        }


        public ConfigBuilder animate(int animationId) {
            this.dontAnimate = false;
            this.animationType = AnimationMode.ANIMATIONID;
            this.animationId = animationId;
            return this;
        }

        public ConfigBuilder animate(ViewPropertyAnimation.Animator animator) {
            this.dontAnimate = false;
            this.animationType = AnimationMode.ANIMATOR;
            this.animator = animator;
            return this;
        }

        public ConfigBuilder animate(Animation animation) {
            this.dontAnimate = false;
            this.animationType = AnimationMode.ANIMATION;
            this.animation = animation;
            return this;
        }

        public ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = oWidth;
            this.oHeight = oHeight;
            return this;
        }

        public ConfigBuilder scale(ScaleMode scaleMode) {
            this.scaleMode = scaleMode;
            return this;
        }

        public ConfigBuilder skipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        public ConfigBuilder diskCacheStrategy(DiskCacheMode diskCacheMode) {
            this.diskCacheMode = diskCacheMode;
            return this;
        }

        public ConfigBuilder listener(ImageLoadeListener loadeListener) {
            this.loadeListener = loadeListener;
            return this;
        }

        public ConfigBuilder priority(PriorityMode priorityMode) {
            this.priorityMode = priorityMode;
            return this;
        }

        public void into(View targetView) {
            this.targetView = targetView;
            new ImageLoderConfig(this).display();
        }
    }
}
