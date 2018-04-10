package com.mobile.androidtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2018/4/9 14:56
 * description: 绘制包含多种颜色圆弧带动画的控件
 */

public class MultiColorCircleProgress extends View {

    private final String TAG = getClass().getSimpleName();

    //默认动画时间
    public static final int DEFAULT_ANIM_TIME = 1000;
    //默认剩余百分比
    public static final float DEFAULT_PERCENT = 0.5f;
    //默认圆弧宽度
    public static final int DEFAULT_ARC_WIDTH = 15;
    //默认画布旋转角度
    public static final int DEFAULT_ROTATE_ANGLE = -90;
    //默认View宽高.单位为dp
    public static final int DEFAULT_CIRCLE_HEIGHT = 150;

    //默认View宽高
    private int mDefaultSize;

    //绘制圆弧
    private int mArcColor;
    private float mArcWidth;
    private RectF mRectF;
    //当前进度[0.0f,1.0f]
    private float mRemainPercent;
    //动画时间
    private long mAnimTime;
    //属性动画
    private ValueAnimator mAnimator;

    //绘制背景圆弧
    private Paint mBgArcPaint;
    private int mBgArcColor;

    //圆心坐标
    private Point mCenterPoint;

    //单色时画笔
    private Paint mArcPaint;
    //是否是多颜色圆弧
    private boolean isMultiColor;

    //不同圆弧画笔的集合
    private List<Paint> arcPaintList = new ArrayList<>();
    //对应不同圆弧所占total的百分比[0.0f,1.0f]
    private List<Float> arcPercentList = new ArrayList<>();
    //对应不同圆弧最终剩余（占自身的）百分比[0.0f,1.0f]
    private List<Float> remainPercentInSelfList = new ArrayList<>();
    //对应不同圆弧最终使用的百分比占总使用比例的百分比
    private List<Float> percentInTotalUsedList = new ArrayList<>();

    public MultiColorCircleProgress(Context context) {
        this(context, null);
    }

    public MultiColorCircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiColorCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mDefaultSize = dip2px(context, DEFAULT_CIRCLE_HEIGHT);
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mCenterPoint = new Point();
        initAttrs(context, attrs);
        initBgPaint();
        if (!isMultiColor) {
            initPaint();
            setProgress(mRemainPercent);
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiColorCircleProgress);

        mArcWidth = typedArray.getDimension(R.styleable.MultiColorCircleProgress_arcWidth, DEFAULT_ARC_WIDTH);
        mArcColor = typedArray.getColor(R.styleable.MultiColorCircleProgress_arcColor, Color.WHITE);
        mBgArcColor = typedArray.getColor(R.styleable.MultiColorCircleProgress_bgArcColor, Color.WHITE);

        mRemainPercent = typedArray.getFloat(R.styleable.MultiColorCircleProgress_remainPercent, DEFAULT_PERCENT);

        mAnimTime = typedArray.getInt(R.styleable.MultiColorCircleProgress_animTime, DEFAULT_ANIM_TIME);
        isMultiColor = typedArray.getBoolean(R.styleable.MultiColorCircleProgress_isMultiColor, false);

        typedArray.recycle();
    }

    private void initBgPaint() {
        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(true);
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private void initPaint() {
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mArcPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔粗细
        mArcPaint.setStrokeWidth(mArcWidth);
        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        // Cap.ROUND,或方形样式 Cap.SQUARE
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setColor(mArcColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, mDefaultSize),
                getMeasureSize(heightMeasureSpec, mDefaultSize));
    }


    /**
     * 测量设置实际大小
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int getMeasureSize(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        }
        return result;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //求最小值作为实际值
        int minSize = Math.min(w - getPaddingLeft() - getPaddingRight() - 2 * (int) mArcWidth,
                h - getPaddingTop() - getPaddingBottom() - 2 * (int) mArcWidth);
        //减去圆弧的宽度，否则会造成部分圆弧绘制在外围
        int mRadius = minSize / 2;
        //获取圆的相关参数
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        //绘制圆弧的边界
        mRectF.left = mCenterPoint.x - mRadius - mArcWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - mArcWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + mArcWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + mArcWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isMultiColor) {
            drawMultiColorArc(canvas);
        } else {
            drawArc(canvas);
        }

    }


    /**
     * 画多种颜色的圆弧
     *
     * @param canvas
     */
    private void drawMultiColorArc(Canvas canvas) {
        try {
            canvas.save();
            Log.d("huang", "==================================================");
            Log.d("huang", "drawMultiColorArc: mRemainPercent:" + mRemainPercent);
            float usedAngle = 360 * (1 - mRemainPercent);
            // 3点钟方向为0度，顺时针递增
            canvas.rotate(DEFAULT_ROTATE_ANGLE, mCenterPoint.x, mCenterPoint.y);
            canvas.drawArc(mRectF, 0, usedAngle, false, mBgArcPaint);
            // 第一个参数 oval 为 RectF 类型，即圆弧显示区域
            // startAngle 和 sweepAngle  均为 float 类型，分别表示圆弧起始角度和圆弧度数
            // useCenter:如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
            float lastAngle = usedAngle;
            Log.d("huang", "drawMultiColorArc: arcPaintList.size:" + arcPaintList.size());
            for (int i = 0; i < arcPaintList.size(); i++) {
                Log.d("huang", "drawMultiColorArc: i:" + i);
                Log.d("huang", "drawMultiColorArc: usedAngle:" + usedAngle);
                float arcPercent = arcPercentList.get(i);
                //初始总度数
                float curTotalAngle = 360 * arcPercent;
                Log.d("huang", "drawMultiColorArc: curTotalAngle:" + curTotalAngle);
                float percentInTotalUsed = percentInTotalUsedList.get(i);
                Log.d("huang", "drawMultiColorArc: percentInTotalUsed:" + percentInTotalUsed);
                float curUsedAngle = usedAngle * percentInTotalUsed;
                Log.d("huang", "drawMultiColorArc: curUsedAngle:" + curUsedAngle);
                //圆弧度数(剩余度数) = 初始总度数 - 已用度数
                float sweepAngle = curTotalAngle - curUsedAngle;
                Log.d("huang", "drawMultiColorArc: sweepAngle:" + sweepAngle);
                canvas.drawArc(mRectF, lastAngle, sweepAngle, false, arcPaintList.get(i));
                lastAngle += sweepAngle;
            }
            Log.d("huang", "==================================================");
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("huang", "drawMultiColorArc: e:" + e.getLocalizedMessage() );
        }
    }


    /**
     * 画单色圆弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        float usedAngle = 360 * (1 - mRemainPercent);
        float remainAngle = 360 - usedAngle;
        // 3点钟方向为0度，顺时针递增
        canvas.rotate(DEFAULT_ROTATE_ANGLE, mCenterPoint.x, mCenterPoint.y);
        canvas.drawArc(mRectF, 0, usedAngle, false, mBgArcPaint);
        // 第一个参数 oval 为 RectF 类型，即圆弧显示区域
        // startAngle 和 sweepAngle  均为 float 类型，分别表示圆弧起始角度和圆弧度数
        // useCenter:如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        canvas.drawArc(mRectF, usedAngle, remainAngle, false, mArcPaint);
        canvas.restore();
    }

    /**
     * 设置最终剩余的百分比[0.0f,1.0f]开始动画
     * 动效为从1到0逐渐减少
     *
     * @param percent
     */
    public void setProgress(float percent) {
        percent = formatPercent(percent);
        if (percent == 0) {
            startAnimator(1, percent, 0);
        } else {
            startAnimator(1, percent, mAnimTime);
        }
    }


    /**
     * 格式化百分比数据，数值范围规范在[0.0,1.0]
     * @param percent
     * @return
     */
    private float formatPercent(float percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 1) {
            percent = 1;
        }
        return percent;
    }


    /**
     * 设置多色圆弧进度。三个List必须一一对应
     * @param arcPaintColorList  不同圆弧画笔颜色的集合
     * @param arcPercentList     对应不同圆弧所占total的百分比[0.0f,1.0f]
     * @param remainPercentInSelfList 对应不同圆弧最终剩余（占自身的）百分比[0.0f,1.0f]
     */
    public void setMultiColorProgress(List<Integer> arcPaintColorList,
                                      List<Float> arcPercentList,
                                      List<Float> remainPercentInSelfList) {
        if (!isValidate(arcPaintColorList,arcPercentList,remainPercentInSelfList)) {
            Log.e(TAG, "设置多种颜色圆弧数据不合法");
            return;
        }
        formatPercentList(arcPercentList);
        formatPercentList(remainPercentInSelfList);

        float mUsedPercent = 0;
        for (int i = 0; i < arcPercentList.size(); i++) {
            float arcPercent = arcPercentList.get(i);
            float arcUsedPercent = 1 - remainPercentInSelfList.get(i);
            mUsedPercent += arcPercent * arcUsedPercent;
        }

        mRemainPercent = 1 - mUsedPercent;

        if (percentInTotalUsedList.size() > 0) {
            percentInTotalUsedList.clear();
        }

        float curPercent = 0;
        for (int i = 0; i < arcPercentList.size(); i++) {
            float arcPercent = arcPercentList.get(i);
            float arcUsedPercent = 1 - remainPercentInSelfList.get(i);
            float remainPercent = arcPercent * arcUsedPercent / mUsedPercent;
            if (i == arcPercentList.size() - 1) {
                percentInTotalUsedList.add(1.0f - curPercent);
            } else {
                curPercent += remainPercent;
                percentInTotalUsedList.add(remainPercent);
            }
        }

        this.arcPercentList = arcPercentList;
        this.remainPercentInSelfList = remainPercentInSelfList;
        isMultiColor = true;

        //初始化画笔集合
        initArcPaintList(arcPaintColorList);
        //开始执行动画
        setProgress(mRemainPercent);
    }

    /**
     * 对传入的数据进行格式化，防止异常数据
     * @param percentList
     */
    private void formatPercentList(List<Float> percentList) {
        for (int i = 0; i < percentList.size(); i++) {
            percentList.set(i, formatPercent(percentList.get(i)));
        }
    }

    /**
     * 检验数据是否合法
     * @param arcPaintColorList
     * @param arcPercentList
     * @param remainPercentInSelfList
     * @return
     */
    private boolean isValidate(List<Integer> arcPaintColorList,
                               List<Float> arcPercentList,
                               List<Float> remainPercentInSelfList) {
        if (listIsNull(arcPaintColorList) || listIsNull(arcPercentList)
                || listIsNull(remainPercentInSelfList)) {
            return false;
        }
        int size1 = arcPaintColorList.size();
        int size2 = arcPercentList.size();
        int size3 = remainPercentInSelfList.size();
        if (size1 != size2 || size1 != size3) {
            return false;
        }
        float totalPercent = 0;
        for (int i = 0; i < arcPercentList.size(); i++) {
            totalPercent += arcPercentList.get(i);
        }
        if (totalPercent != 1) {
            return false;
        }
        return true;
    }

    private boolean listIsNull(List arcPaintColorList) {
        return arcPaintColorList == null || arcPaintColorList.size() == 0;
    }


    /**
     * 初始化画笔集合
     *
     * @param arcPaintColorList
     */
    private void initArcPaintList(List<Integer> arcPaintColorList) {
        if (arcPaintList.size() > 0) {
            arcPaintList.clear();
        }
        for (int i = 0; i < arcPaintColorList.size(); i++) {
            int arcColor = arcPaintColorList.get(i);
            Paint mArcPaint = new Paint();
            mArcPaint.setAntiAlias(true);
            // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
            mArcPaint.setStyle(Paint.Style.STROKE);
            // 设置画笔粗细
            mArcPaint.setStrokeWidth(mArcWidth);
            // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
            // Cap.ROUND,或方形样式 Cap.SQUARE
            mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            mArcPaint.setColor(arcColor);
            arcPaintList.add(mArcPaint);
        }

    }

    /**
     * 执行动画
     *
     * @param start
     * @param end
     * @param animTime
     */
    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRemainPercent = (float) animation.getAnimatedValue();
                Log.d("huang", "onAnimationUpdate: mRemainPercent:" + mRemainPercent);
                invalidate();
            }
        });
        mAnimator.start();
    }

    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    public int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}