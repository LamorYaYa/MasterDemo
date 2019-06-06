package com.master.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.app.R;

/**
 * Create By Master
 * On 2019/5/15 15:57
 */
public class DotView extends LinearLayout {

    private TextView textView;

    public DotView(Context context) {
        this(context, null);
    }

    public DotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.dot_layout, this, true);
        this.setVisibility(GONE);
        textView = findViewById(R.id.num_tv);
    }


    public void setShowNum(int num) {
        this.setVisibility(VISIBLE);
        textView.setText(String.valueOf(num));
    }


}
