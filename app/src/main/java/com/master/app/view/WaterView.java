package com.master.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Create By Master
 * On 2019/5/16 17:27
 * 水波纹View
 */
public class WaterView extends View {


    private static final int WATER_PAINT_COLOR = 0x880000aa;// 波纹颜色

    private Canvas mCanvas;
    private Paint mWaterPaint;
    private DrawFilter mDrawFilter;

    private int mWidth;
    private int mHeight;

    private Path mPath;


    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.XOR);


    public WaterView(Context context) {
        this(context, null);
    }

    public WaterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        startAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    private void initViews() {
        mWaterPaint = new Paint();
        mWaterPaint.setAntiAlias(true);// 去锯齿
        mWaterPaint.setStyle(Paint.Style.FILL);// 实线
        mWaterPaint.setColor(WATER_PAINT_COLOR);// 画笔颜色

        mWaterPaint.setXfermode(mMode);

        // 设置画布抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mPath = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);// 设置画布抗锯齿


        mPath.reset();
        mPath.moveTo(-mWidth, mHeight / 2);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        canvas.drawPath(mPath, mWaterPaint);


        canvas.drawCircle(100, 100, 50, mWaterPaint);

        canvas.drawRect(100, 100, 200, 200, mWaterPaint);



    }


    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(200);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
                float value = (Float) animation.getAnimatedValue();
                Log.e("TAG", "iiiiiiiiii = " + value);
            }
        });
        animator.start();
    }


}
