package com.master.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.master.app.R;

/**
 * Create By Master
 * On 2019/1/28 11:15
 *
 *
 *
 */

@SuppressLint("AppCompatCustomView")
public class ICustomButton extends Button {

    private int backColor;// 背景颜色
    private int backColorPress;// 按下时背景色
    private Drawable backGroundDrawable;// 背景图片
    private Drawable backGroundDrawablePress;// 按下时背景图片
    private ColorStateList textColor;// 文字颜色
    private ColorStateList textColorPress;// 按下时文字颜色

    private boolean isDrawable; // 是否是设置图片背景

    private GradientDrawable gradientDrawable;

    public ICustomButton(Context context) {
        this(context, null);
    }

    public ICustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ICustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.iCustomButton, defStyleAttr, 0);
        if (typedArray != null) {
            // 设置文字颜色
            textColor = typedArray.getColorStateList(R.styleable.iCustomButton_textColor);
            if (textColor != null) {
                setTextColor(textColor);
            }
            // 记录文字按下是颜色
            textColorPress = typedArray.getColorStateList(R.styleable.iCustomButton_textColorPress);

            isDrawable = typedArray.getBoolean(R.styleable.iCustomButton_isDrawable, false);
            if (isDrawable) {
                // 设置背景图片
                backGroundDrawable = typedArray.getDrawable(R.styleable.iCustomButton_backGroundImage);
                if (backGroundDrawable != null) {
                    setBackgroundDrawable(backGroundDrawable);
                }
                // 记录按下时背景图片
                backGroundDrawablePress = typedArray.getDrawable(R.styleable.iCustomButton_backGroundImagePress);

            } else {
                getGradientDrawable();
                // 设置背景色
                ColorStateList colorStateList = typedArray.getColorStateList(R.styleable.iCustomButton_backColor);
                if (colorStateList != null) {
                    backColor = colorStateList.getColorForState(getDrawableState(), 0);
                    if (backColor != 0) {
                        gradientDrawable.setColor(backColor);
                        setBackgroundDrawable(gradientDrawable);
                    }
                }

                // 记录按钮被按下时的背景色
                ColorStateList colorStateListPress = typedArray.getColorStateList(R.styleable.iCustomButton_backColorPress);
                if (colorStateListPress != null) {
                    backColorPress = colorStateListPress.getColorForState(getDrawableState(), 0);
                }


                // 设置圆角
                float radius = typedArray.getFloat(R.styleable.iCustomButton_radius, 0);
                if (radius != 0) {
                    setRadius(radius);
                }
                // 设置按钮形状
                int shape = typedArray.getInteger(R.styleable.iCustomButton_shape, 0);
                if (shape != 0) {
                    setShape(shape);
                }
            }
            typedArray.recycle();
            setOnTouchListener((v, event) -> setTouchStyle(event.getAction()));


        }
    }


    /**
     * 根据按下或者抬起来改变背景和文字样式
     * 为解决onTouch和onClick冲突的问题
     * 根据事件分发机制，如果onTouch返回true，则不响应onClick事件
     * 因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
     *
     * @param state
     * @return
     */
    private boolean setTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {

            if (textColorPress != null) {
                setTextColor(textColorPress);
            }

            if (isDrawable) {
                if (backGroundDrawablePress != null) {
                    setBackgroundDrawable(backGroundDrawablePress);
                }
            } else {
                if (backColorPress != 0) {
                    gradientDrawable.setColor(backColorPress);
                    setBackgroundDrawable(gradientDrawable);
                }
            }


        } else if (state == MotionEvent.ACTION_UP) {

            if (textColor != null) {
                setTextColor(textColor);
            }

            if (isDrawable) {
                if (backGroundDrawable != null) {
                    setBackgroundDrawable(backGroundDrawable);
                }
            } else {
                if (backColor != 0) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                }
            }
        }

        return false;
    }


    /**
     * 设置圆角
     *
     * @param radius
     */
    private void setRadius(float radius) {
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackgroundDrawable(gradientDrawable);
    }


    /**
     * 设置按钮形状
     *
     * @param shape
     */
    private void setShape(int shape) {
        getGradientDrawable();
        gradientDrawable.setShape(shape);
        setBackgroundDrawable(gradientDrawable);
    }


    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }


}
