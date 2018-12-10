package com.master.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.app.R;

/**
 * Create By Master
 * On 2018/11/29 10:57
 */
public class IDefaultDialog {

    private Context iContext;
    private Dialog iDialog;
    private Display display;
    private Window iDialogWindow;

    private TextView iDialogTitle;
    private TextView iDialogMessage;
    private TextView iDialogCancel;
    private TextView iDialogOk;

    /**
     * 构造函数
     *
     * @param iContext
     */
    public IDefaultDialog(Context iContext) {
        this.iContext = iContext;
        WindowManager windowManager = (WindowManager) iContext.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        iDialog = new Dialog(iContext, R.style.CustomDialogStyle);
        iDialogWindow = iDialog.getWindow();
    }

    /**
     * 初始化View
     *
     * @return
     */
    public IDefaultDialog builder() {
        final View view = LayoutInflater.from(iContext).inflate(R.layout.layout_custom_dialog, null, false);

        LinearLayout linearLayout = view.findViewById(R.id.dialog_group);

        iDialogTitle = view.findViewById(R.id.dialog_title);
        iDialogMessage = view.findViewById(R.id.dialog_message);
        iDialogCancel = view.findViewById(R.id.dialog_cancel);
        iDialogOk = view.findViewById(R.id.dialog_ok);

        iDialog.setContentView(view);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(((int) (display.getWidth() * 0.80)), LinearLayout.LayoutParams.WRAP_CONTENT));
        iDialogWindow.setGravity(Gravity.CENTER);
        return this;
    }

    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public IDefaultDialog setTitle(String text) {
        if (text == null || "".equals(text)) {
            iDialogTitle.setText("TITLE");
        } else {
            iDialogTitle.setText(text);
        }
        return this;
    }

    /**
     * 设置标题，可配置颜色
     *
     * @param text
     * @param color
     * @return
     */
    public IDefaultDialog setTitle(String text, int color) {
        if (text == null || "".equals(text)) {
            iDialogTitle.setText("TITLE");
        } else {
            iDialogTitle.setText(text);
        }
        iDialogTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置内容
     *
     * @param text
     * @return
     */
    public IDefaultDialog setSubTitle(String text) {
        if (text == null || "".equals(text)) {
            iDialogMessage.setText("TITLE");
        } else {
            iDialogMessage.setText(text);
        }
        return this;
    }

    /**
     * 设置内容，可配置颜色
     *
     * @param text
     * @param color
     * @return
     */
    public IDefaultDialog setSubTitle(String text, int color) {
        if (text == null || "".equals(text)) {
            iDialogMessage.setText("TITLE");
        } else {
            iDialogMessage.setText(text);
        }
        iDialogMessage.setTextColor(color);
        return this;
    }

    /**
     * 设置左侧Button
     *
     * @param text
     * @param listener
     * @return
     */
    public IDefaultDialog setLeftButton(String text, final View.OnClickListener listener) {
        if (text == null || "".equals(text)) {
            iDialogCancel.setText("取消");
        } else {
            iDialogCancel.setText(text);
        }
        iDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                iDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置左侧Button，可配置字体颜色
     *
     * @param text
     * @param color
     * @param listener
     * @return
     */
    public IDefaultDialog setLeftButton(String text, int color, final View.OnClickListener listener) {
        if (text == null || "".equals(text)) {
            iDialogCancel.setText("取消");
        } else {
            iDialogCancel.setText(text);
        }
        iDialogCancel.setTextColor(color);
        iDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                iDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置右侧Button
     *
     * @param text
     * @param listener
     * @return
     */
    public IDefaultDialog setRightButton(String text, final View.OnClickListener listener) {
        if (text == null || "".equals(text)) {
            iDialogOk.setText("确定");
        } else {
            iDialogOk.setText(text);
        }

        iDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                iDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置右侧Button，可配置字体颜色
     *
     * @param text
     * @param color
     * @param listener
     * @return
     */
    public IDefaultDialog setRightButton(String text, int color, final View.OnClickListener listener) {
        if (text == null || "".equals(text)) {
            iDialogOk.setText("确定");
        } else {
            iDialogOk.setText(text);
        }
        iDialogOk.setTextColor(color);
        iDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                iDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置相对位置
     */
    public IDefaultDialog setGravity(int gravity) {
        iDialogWindow.setGravity(gravity);
        return this;
    }

    /**
     * 设置是否可以取消
     */
    public IDefaultDialog setCancelable(boolean cancelable) {
        iDialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置点击空白是否可以取消
     *
     * @param cancelable
     * @return
     */
    public IDefaultDialog setCanceledOnTouchOutside(boolean cancelable) {
        iDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }


    /**
     * show
     */
    public void show() {
        iDialog.show();
    }

    /**
     * 隐藏Dialog
     */
    public void dismiss() {
        iDialog.dismiss();
    }
}
