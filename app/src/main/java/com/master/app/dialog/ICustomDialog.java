package com.master.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.master.app.R;

/**
 * Create By Master
 * On 2018/11/29 10:18
 */
public class ICustomDialog extends Dialog implements OnClickListener {

    private static Context iContext;
    private static int iLayoutResId;
    private static int[] iListenedItems = new int[]{};

    // dialog 动画
    private static int iAnimationResId;

    // Dialog 相对页面显示的位置
    private static int iPosition = 0;

    // 默认点击空白区域消失
    private static boolean iCanceledOnTouchOutside = true;

    // 默认点击返回键消失
    private static boolean iCancelable = true;

    // 回掉自定义接口
    private static OnDialogItemClickListener iOnDialogItemClickListener;


    public interface OnDialogItemClickListener {
        void onDialogItemClick(ICustomDialog thisDialog, View clickView);
    }


    public ICustomDialog() {
        super(iContext, R.style.CustomDialogStyle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (0 == iPosition) {
            // 默认居中
            window.setGravity(Gravity.CENTER);
        } else {
            // 设置要显示的位置
            window.setGravity(iPosition);
        }

        if (iAnimationResId == 0) {
            // 添加默认动画
            window.setWindowAnimations(R.style.BottomAnimation);
        } else {
            // 添加自定义动画
            window.setWindowAnimations(iAnimationResId);
        }
        setContentView(iLayoutResId);

        setCanceledOnTouchOutside(iCanceledOnTouchOutside);
        setCancelable(iCancelable);

        for (int id : iListenedItems) {
            findViewById(id).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (iOnDialogItemClickListener != null) {
            iOnDialogItemClickListener.onDialogItemClick(this, v);
        }
    }


    public static class Builder {

        public Builder(Context context) {
            iContext = context;
        }

        public Builder setLayoutResId(int layoutResId) {
            iLayoutResId = layoutResId;
            return this;
        }

        public Builder setListenedItems(int[] listenedItems) {
            iListenedItems = listenedItems;
            return this;
        }

        public Builder setAnimationResId(int animationResId) {
            iAnimationResId = animationResId;
            return this;
        }

        public Builder setDialogPosition(int position) {
            iPosition = position;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            iCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            iCancelable = cancelable;
            return this;
        }

        public Builder setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
            iOnDialogItemClickListener = onDialogItemClickListener;
            return this;
        }

        public ICustomDialog build() {
            return new ICustomDialog();
        }
    }


}
