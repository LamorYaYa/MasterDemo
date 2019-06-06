package com.master.app.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Create By Master
 * On 2019/5/16 12:51
 */
public class ViewAnimUtils {


    /**
     * 放大缩小动画，从0-1,再从1-0.95. 视觉感
     *
     * @param view     执行动画的View
     * @param start    0~1
     * @param end      0~1
     * @param duration 时间
     * @param isWhile  true代表有缩放，false 没有
     */

    public static void showAnim(final View view, float start, final float end, int duration, final boolean isWhile) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setPivotX(view.getWidth());
                view.setPivotY(0);
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isWhile) {
                    showAnim(view, end, 0.95f, duration / 3, false);
                }
            }
        });
        valueAnimator.start();
    }


}
