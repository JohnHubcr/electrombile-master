package com.zenchn.electrombile.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.zenchn.electrombile.R;
import com.zenchn.mlibrary.log.LogUtils;


public class CircleProgressBar extends View {

    /**
     * 画笔对象的引用
     * 因为渲染会对整个画笔进行渲染，所以定义了两个
     */
    private Paint paint;//绘制背景的画笔
    private Paint paintProgress;//绘制进度条的画笔

    private final int defaultWhite = getResources().getColor(R.color.white);
    private final int defaultOrange = getResources().getColor(R.color.alpha_ring);
    private final int defaultOrangeTint = getResources().getColor(R.color.alpha_ring_bg);
    private final int defaultBackground = getResources().getColor(R.color.module_normal_bg);

    private int precision = 2;//绘制精度

    private boolean borderIsDisplay;//是否显示边缘
    private int borderColor;//边缘色
    private float edgeWidth;//边缘宽度
    private float progressScale;//进度条内缩比例
    private int roundBackgroundColor;//圆环背景的颜色
    private int roundProgressColor;//圆环进度的颜色
    //    private float roundWidth;//圆环的宽度
    private float roundWidthScale;//圆环的宽度比例
    private int textColor;//中间进度百分比的颜色
    private float textUnitSize;//上面单位的字号
    private String textUnit;//单位的类型
    private float textSize;//中间进度百分比的字号
    private boolean textIsDisplayable;//是否显示中间
    private int max;//最大进度
    private int progress;//当前进度
    private final int startDegree;//起始角度
    private final int maxDegree;//最大角度
    private ArgbEvaluator argbEvaluator;//估值器

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        paintProgress = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CircleProgressBar);

        //获取自定义属性和默认值
        borderIsDisplay = mTypedArray.getBoolean(R.styleable.CircleProgressBar_borderIsDisplay, true);//是否显示边缘
        borderColor = mTypedArray.getColor(R.styleable.CircleProgressBar_borderColor, getResources().getColor(R.color.translucent));//边缘颜色
        edgeWidth = mTypedArray.getDimension(R.styleable.CircleProgressBar_edgeWidth, 2);//边缘宽度
        progressScale = mTypedArray.getFloat(R.styleable.CircleProgressBar_progressScale, 0.85f);//进度条缩放比例
        roundBackgroundColor = mTypedArray.getColor(R.styleable.CircleProgressBar_roundColor, defaultOrangeTint);//背景色
        roundProgressColor = mTypedArray.getColor(R.styleable.CircleProgressBar_roundProgressColor, defaultOrange);//进度色
        roundWidthScale = mTypedArray.getFloat(R.styleable.CircleProgressBar_roundWidthScale, 0.6f);//进度条宽度比例
        textColor = mTypedArray.getColor(R.styleable.CircleProgressBar_textColor, defaultWhite);//文字颜色
        textSize = mTypedArray.getDimension(R.styleable.CircleProgressBar_textSize, 14);//字体大小
        textUnitSize = mTypedArray.getDimension(R.styleable.CircleProgressBar_textUnitSize, 9);//字体大小
        textUnit = mTypedArray.getString(R.styleable.CircleProgressBar_textUnit);//单位
        max = mTypedArray.getInteger(R.styleable.CircleProgressBar_max, 100);//进度条最大值
        startDegree = mTypedArray.getInteger(R.styleable.CircleProgressBar_startDegree, -225);//进度条起始角度
        maxDegree = mTypedArray.getInteger(R.styleable.CircleProgressBar_maxDegree, 270);//进度条最大值
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.CircleProgressBar_textIsDisplayable, true);//是否显示中间

        mTypedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int diameter = getWidth();//控件宽度
        int radius = diameter / 2; //获取圆心的x坐标

        /**
         * 1.先画出边缘
         */
        if (borderIsDisplay) {
            paint.setStyle(Paint.Style.STROKE); //设置空心
            paint.setAntiAlias(true);  //消除锯齿
            drawBorder(canvas, paint, diameter, edgeWidth);//画出边缘
        }

        /**
         * 2.在画出内圆环背景
         */
        int offset = (int) ((1 - progressScale) * radius); //圆环内缩偏移量
        argbEvaluator = new ArgbEvaluator();
        float roundWidth = (radius - offset) * roundWidthScale;
//        offset = 25;
//        roundWidth = 40f;
        drawArc(canvas, paint, offset, diameter - offset, roundWidth, startDegree, maxDegree, defaultOrangeTint, defaultBackground, argbEvaluator, 2);

        /**
         * 3.画圆环的进度
         */
        paintProgress.setStyle(Paint.Style.STROKE); //设置空心
        paintProgress.setAntiAlias(true);  //消除锯齿
        int progressDegree = maxDegree * progress / 100;
        drawArc(canvas, paintProgress, offset, diameter - offset, roundWidth, startDegree, progressDegree, defaultOrange, defaultBackground, argbEvaluator, 5);

        /**
         * 4.画进度百分比
         */
        drawText(canvas, paintProgress, progressDegree, radius);//画出进度百分比
    }

    /**
     * 绘制边缘
     *
     * @param canvas
     * @param paint
     * @param diameter
     * @param borderWidth
     */
    private void drawBorder(Canvas canvas, Paint paint, int diameter, float borderWidth) {
        RectF mOutSideOva = new RectF(borderWidth, borderWidth, diameter - borderWidth, diameter - borderWidth);
        paint.setStrokeWidth(borderWidth); //设置圆环的宽度
        paint.setColor(borderColor); //设置外层圆环的颜色
        canvas.drawArc(mOutSideOva, startDegree, maxDegree, false, paint);
    }

    /**
     * 绘制中心文本
     *
     * @param canvas
     * @param paint
     * @param progressDegree
     * @param radius
     */
    private void drawText(Canvas canvas, Paint paint, float progressDegree, float radius) {
        if (!textIsDisplayable)
            return;
        paint.setStrokeWidth(0);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
//        if (textUnit != null && TextUtils.isEmpty(textUnit)) {
//            paint.setTextSize(textUnitSize);
//            float textWidth = paint.measureText(textUnit);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
//            canvas.drawText(textUnit, radius - textWidth / 2, radius + textWidth / 2, paint); //画出进度百分比
//        }
        paint.setTextSize(textSize);
        int percent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(String.valueOf(percent));   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(String.valueOf(percent), radius - textWidth / 2, radius + textWidth / 2, paint); //画出进度百分比
    }

    /**
     * 绘制渐变圆环
     *
     * @param canvas
     * @param paint
     * @param left         圆环最左边坐标
     * @param right        圆环最右边坐标
     * @param roundWidth   圆环宽度
     * @param roundWidth   圆环宽度
     * @param startAngle   起始角度
     * @param sweepAngl    绘制角度
     * @param roundBgColor 圆环背景颜色
     * @param bgColor      背景颜色
     * @param precision    绘制精度
     */
    private void drawArc(Canvas canvas, Paint paint, int left, int right, float roundWidth, float startAngle, float sweepAngl, int roundBgColor, int bgColor, ArgbEvaluator argbEvaluator, int
            precision) {
        float reviseIndexValue = 1f / precision;//修正后的分度
        paint.setStrokeWidth(reviseIndexValue); //设置绘制的宽度
        LogUtils.printCustomLog("X", "分度：" + reviseIndexValue);
        float reviseWidth = roundWidth * precision;//修正后的宽度
        LogUtils.printCustomLog("X", "绘制的宽度：" + reviseWidth);
        int roundColor;

        for (int i = 0; i < reviseWidth; i++) {
            float reviseValue = reviseIndexValue * i;
            RectF mBigOva = new RectF(left + reviseValue, left + reviseValue, right - reviseValue, right - reviseValue);
            roundColor = (int) argbEvaluator.evaluate(i / reviseWidth, roundBgColor, bgColor);
            paint.setColor(roundColor); //设置圆环的颜色
            canvas.drawArc(mBigOva, startAngle, sweepAngl, false, paint); //画出圆弧
        }
    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(float progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = (int) progress;
            postInvalidate();
        }

    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }


}

