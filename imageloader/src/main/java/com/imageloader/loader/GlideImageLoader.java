package com.imageloader.loader;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.imageloader.callback.ImageLoadeListener;
import com.imageloader.config.ImageLoderConfig;
import com.imageloader.mode.AnimationMode;
import com.imageloader.mode.DiskCacheMode;
import com.imageloader.mode.PriorityMode;
import com.imageloader.mode.ScaleMode;
import com.imageloader.mode.TransformationMode;
import com.imageloader.transformations.ColorFilterTransformation;
import com.imageloader.transformations.CropCircleTransformation;
import com.imageloader.transformations.GrayscaleTransformation;
import com.imageloader.transformations.RoundedCornersTransformation;

/**
 * author：hj
 * time: 2017/6/7 0007 09:21
 * 使用Glide实现加载图片
 */

public class GlideImageLoader implements ILoader {

    @Override
    public void display(ImageLoderConfig config) {
        RequestManager requestManager = Glide.with(config.getContext());
        DrawableTypeRequest drawableTypeRequest = getDrawableTypeRequest(requestManager, config);
        final ImageLoadeListener listener = config.getLoadeListener();
        if (drawableTypeRequest == null) {
            if (listener != null) {
                listener.onLoadFailed(new RuntimeException("未设置要加载的图片资源"));
            }
            return;
        } else {
            //占位符图片
            int placeholderResId = config.getPlaceholderResId();
            if (placeholderResId > 0) {
                drawableTypeRequest.placeholder(placeholderResId);
            }
            //加载失败图片
            int errorResId = config.getErrorResId();
            if (errorResId > 0) {
                drawableTypeRequest.error(errorResId);
            }
            //设置是否跳过内存缓存
            boolean isSkipMemoryCache = config.isSkipMemoryCache();
            drawableTypeRequest.skipMemoryCache(isSkipMemoryCache);

            //设置加载优先级
            PriorityMode priorityMode = config.getPriorityMode();
            if (priorityMode != null) {
                setPriorityMode(priorityMode, drawableTypeRequest);
            }

            //设置缓存策略
            DiskCacheMode diskCacheMode = config.getDiskCacheMode();
            if (diskCacheMode != null) {
                setDiskCacheMode(diskCacheMode, drawableTypeRequest);
            }

            //设置加载尺寸
            int oWidth = config.getoWidth();
            int oHeight = config.getoHeight();
            if (oWidth > 0 && oHeight > 0) {
                drawableTypeRequest.override(oWidth, oHeight);
            }

            //设置缩放模式
            ScaleMode scaleMode = config.getScaleMode();
            if (scaleMode != null) {
                setScleMode(scaleMode, drawableTypeRequest);
            }

            //设置加载监听
            if (listener != null) {
                drawableTypeRequest.listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onLoadFailed(e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onLoadCompleted(resource);
                        }
                        return false;
                    }
                });
            }

            //日志监听。开发调试时使用。上线前关闭
//            drawableTypeRequest.listener(new LoggingListener<String, GlideDrawable>());

            //是否以Gif形式加载资源
            if (config.isGif()) {
                drawableTypeRequest.asGif();
            }

            //加载Gif时是否只显示Gif第一帧
            if (config.isAsBitmap()) {
                drawableTypeRequest.asBitmap();
            }

            //设置图片加载动画
            setAnimator(config, drawableTypeRequest);

            setTransformation(config, drawableTypeRequest);

            //设置是否取消加载动画
            boolean isDontAnimate = config.isDontAnimate();
            if (isDontAnimate) {
                drawableTypeRequest.dontAnimate();
            }

            View targetView = config.getTargetView();
            if (targetView instanceof ImageView) {
                drawableTypeRequest.into((ImageView) targetView);
            }
        }

    }

    @Override
    public void clearMomoryCache(View view) {
        Glide.clear(view);
    }

    @Override
    //清理内存缓存 在UI主线程中进行
    public void clearMomory(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    //清理磁盘缓存 需要在子线程中执行
    public void clearDiskCache(final Context context) {
       new Thread(){
           @Override
           public void run() {
               super.run();
               Glide.get(context).clearDiskCache();
           }
       }.start();
    }

    @Override
    public void clearAllMemoryCaches(Context context) {
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
//    @Override
//    public void setDiskCache(Context context, String diskCachePath,
//                             String diskCacheName, int diskCacheSize) {
//        GlideBuilder builder = new GlideBuilder(context);
//        diskCacheSize = 1024 * 1024 * diskCacheSize;//最多可以缓存多少字节的数据
//        builder.setDiskCache(new DiskLruCacheFactory(diskCachePath, diskCacheName, diskCacheSize));
//    }

    private void setTransformation(ImageLoderConfig config, DrawableTypeRequest drawableTypeRequest) {
        TransformationMode transformationMode = config.getTransformationMode();
        if (transformationMode == null) {
            return;
        }
        Context context = config.getContext();
        switch (transformationMode) {
            case CropCircleTransformation://圆圈
                int borderWidth = config.getBorderWidth();
                int borderColor = config.getBorderColor();
                if (borderWidth <= 0) {
                    drawableTypeRequest.bitmapTransform(new CropCircleTransformation(context));
                } else {
                    drawableTypeRequest.bitmapTransform(new CropCircleTransformation(context, borderWidth, borderColor));
                }
                break;
            case GrayscaleTransformation://灰色蒙版
                drawableTypeRequest.bitmapTransform(new GrayscaleTransformation(context));
                break;
            case ColorFilterTransformation://颜色滤镜
                drawableTypeRequest.bitmapTransform(new ColorFilterTransformation(context, config.getTransformColor()));
                break;
            case RoundedCornersTransformation://圆角矩形
                drawableTypeRequest.bitmapTransform(
                        new RoundedCornersTransformation(context, config.getRoundedCornersRadius(), config.getRoundedCornersMargin()));
                break;
        }
    }


    /**
     * 设置加载动画
     *
     * @param config
     * @param drawableTypeRequest
     */
    private void setAnimator(ImageLoderConfig config, DrawableTypeRequest drawableTypeRequest) {
        AnimationMode animationMode = config.getAnimationType();
        if (animationMode == null) {
            return;
        }
        switch (animationMode) {
            case ANIMATIONID:
                drawableTypeRequest.animate(config.getAnimationId());
                break;
            case ANIMATION:
                drawableTypeRequest.animate(config.getAnimation());
                break;
            case ANIMATOR:
                drawableTypeRequest.animate(config.getAnimator());
                break;

        }
    }


    /**
     * 设置缩放模式
     *
     * @param scaleMode
     * @param drawableTypeRequest
     */
    private void setScleMode(ScaleMode scaleMode, DrawableTypeRequest drawableTypeRequest) {
        switch (scaleMode) {
            case FITCENTER:
                drawableTypeRequest.fitCenter();
                break;
            case CENTERCROP:
                drawableTypeRequest.centerCrop();
                break;
        }
    }


    /**
     * 设置缓存策略
     *
     * @param diskCacheMode
     * @param drawableTypeRequest
     */
    private void setDiskCacheMode(DiskCacheMode diskCacheMode, DrawableTypeRequest drawableTypeRequest) {
        switch (diskCacheMode) {
            case ALL://缓存源资源和转换后的资源
                drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case NONE://缓存源资源和转换后的资源
                drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case SOURCE://缓存源资源和转换后的资源
                drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case RESULT://缓存源资源和转换后的资源
                drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
        }
    }

    /**
     * 设置加载图片优先级
     *
     * @param priorityMode
     * @param drawableTypeRequest
     */
    private void setPriorityMode(PriorityMode priorityMode, DrawableTypeRequest drawableTypeRequest) {
        switch (priorityMode) {
            case IMMEDIATE:
                drawableTypeRequest.priority(Priority.IMMEDIATE);
                break;
            case HIGH:
                drawableTypeRequest.priority(Priority.HIGH);
                break;
            case NORMAL:
                drawableTypeRequest.priority(Priority.NORMAL);
                break;
            case LOW:
                drawableTypeRequest.priority(Priority.LOW);
                break;
        }
    }


    private DrawableTypeRequest getDrawableTypeRequest(RequestManager requestManager, ImageLoderConfig config) {
        DrawableTypeRequest drawableTypeRequest = null;
        if (!TextUtils.isEmpty(config.getLoadStr())) {
            drawableTypeRequest = requestManager.load(config.getLoadStr());
        }else if (config.getLoadResId() > 0) {
            drawableTypeRequest = requestManager.load(config.getLoadResId());
        } else if (config.getLoadFile() != null) {
            drawableTypeRequest = requestManager.load(config.getLoadFile());
        }
        return drawableTypeRequest;
    }

}
