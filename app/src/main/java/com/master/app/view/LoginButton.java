package com.master.app.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

/**
 * Create By Master
 * On 2019/5/10 13:57
 */
@SuppressLint("AppCompatCustomView")
public class LoginButton extends Button {


    private int width;
    private int heigh;

    private GradientDrawable backDrawable;

    private boolean isMorphing;
    private int startAngle;

    private Paint paint;

    private ValueAnimator arcValueAnimator;

    private Context mContext;


    public LoginButton(Context context) {
        this(context, null);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        isMorphing = false;

        backDrawable = new GradientDrawable();
        // 设置背景色
        int colorDrawable = Color.parseColor("#D81B60");
        backDrawable.setColor(colorDrawable);
        backDrawable.setCornerRadius(20);
        setBackground(backDrawable);

        setText("登录");

        paint = new Paint();
        // 设置画笔颜色(画圆圈)
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(2);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heighMode == MeasureSpec.EXACTLY) {
            heigh = heighSize;
        }
    }


    public void endAnim() {
        arcValueAnimator.cancel();
        backDrawable.setBounds(0, 0, width, heigh);
        backDrawable.setCornerRadius(24);
        setBackground(backDrawable);
        setText("登录");
        isMorphing = false;
    }


    public void startAnim() {
        isMorphing = true;

        setText("");
        ValueAnimator valueAnimator = ValueAnimator.ofInt(width, heigh);

        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            int leftOffset = (width - value) / 2;
            int rightOffset = width - leftOffset;

            backDrawable.setBounds(leftOffset, 0, rightOffset, heigh);
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(backDrawable, "cornerRadius", 120, heigh / 2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();

        //画中间的白色圆圈
        showArc();
    }


    private void showArc() {
        arcValueAnimator = ValueAnimator.ofInt(0, 1080);
        arcValueAnimator.addUpdateListener(animation -> {
            startAngle = (int) animation.getAnimatedValue();
            invalidate();
        });
        arcValueAnimator.setInterpolator(new LinearInterpolator());
        arcValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        arcValueAnimator.setDuration(3000);
        arcValueAnimator.start();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (isMorphing == true) {
            float radius;
            if (width > heigh) {
                radius = (heigh * 4 / 5) / 2;
            } else {
                radius = (width * 4 / 5) / 2;
            }

            float L = width / 2 - radius;
            float T = heigh / 2 - radius;
            float R = width / 2 + radius;
            float B = heigh / 2 + radius;

            RectF rectF = new RectF(L, T, R, B);

            canvas.drawArc(rectF, startAngle, 270, false, paint);
        }
    }


}
