package com.hanshao.circleview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hanshaoda
 */
public class ScheduleView extends View {

    /**
     * 最大数值
     */
    private float maxCount = 100f;
    /**
     * 进度值
     */
    private float currentCount = 85f;
    /**
     * 分数值
     */
    private int mCore = 85;
    private Paint mPaint;
    private Paint mTextPaint;
    private int mWidth, mHeight;
    /**
     * 中间的文字
     */
    private String crrentLevel = "安全指数";

    public ScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mTextPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        RectF rectBlackBg = new RectF(20, 20, mWidth - 20, mHeight - 20);
        canvas.drawArc(rectBlackBg, 0, 360, false, mPaint);
        mPaint.setColor(Color.BLACK);
//        根据不同数值设置画笔的颜色
        if (mCore <= 70) {
//            mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTextPaint.setColor(Color.parseColor("#ff0000"));
        } else if (mCore > 70 && mCore <= 90) {
//            mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTextPaint.setColor(Color.parseColor("#ffa500"));
        } else {
//            mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTextPaint.setColor(Color.parseColor("#008000"));
        }
//        画当前的数值
        float v = mTextPaint.measureText(mCore + "");
        canvas.drawText(mCore + "", mWidth / 2 - v / 4, mHeight / 2 + v / 5, mTextPaint);
        mTextPaint.setTextSize(dipToPx(12));
        mTextPaint.setColor(Color.parseColor("#999999"));
//       数值后面的文字（单位）
        canvas.drawText("分", mWidth / 2 + v / 2, mHeight / 2 + v / 5, mTextPaint);
        float v1 = mTextPaint.measureText(crrentLevel);
//       圆进度值中间的介绍文字
        canvas.drawText(crrentLevel, mWidth / 2, mHeight / 2 + v1 / 2,
                mTextPaint);

//       剩余进度的弧形
        float section = currentCount / maxCount;

        if (section <= 0.7f) {
            if (section != 0.0f) {
                mPaint.setColor(Color.parseColor("#ff0000"));
            } else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        } else if (section > 0.7f && section <= 0.9) {
            mPaint.setColor(Color.parseColor("#ffa500"));
        } else {
            mPaint.setColor(Color.parseColor("#008000"));
        }

        canvas.drawArc(rectBlackBg, 270, section * 360, false, mPaint);


    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) dipToPx(12));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.parseColor("#efeff4"));

        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth((float) dipToPx(4));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(dipToPx(35));
        mTextPaint.setColor(Color.parseColor("#efeff4"));

    }

    /**
     * dp转px
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 设置中间文字
     * @param crrentLevel
     */
    public void setCrrentLevel(String crrentLevel) {
        this.crrentLevel = crrentLevel;
    }

    /**
     * 设置当前分数
     * @return
     */
    public void setScore(int score) {
        this.mCore = score;
        invalidate();
    }

    /***
     * 设置最大的进度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY
                || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


}
