package com.kssoft.lake.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kssoft.lake.R;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.ViewUtil;

public class DoubleLinearLayout extends LinearLayout implements TypedBindView {

    @AttrBind
    private String firstTitle;

    @AttrBind
    private String secondTitle;

    @ViewBind
    private TextView tvTitleFirst;

    @ViewBind
    private TextView tvTitleSecond;

    @ViewBind
    private LinearLayout contentFirst;

    @ViewBind
    private LinearLayout contentSecond;

    public DoubleLinearLayout(Context context) {
        super(context);
    }

    public DoubleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public DoubleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int layoutId() {
        return R.layout.layout_double_line;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.DoubleLinearLayout;
    }

    @Override
    public void initView() {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitleFirst.setText(firstTitle);
        tvTitleSecond.setText(secondTitle);

        View firstView = null, secondView = null;
        if (getChildCount() > 1){
            firstView = getChildAt(1);
        }

        if (getChildCount() > 2){
            secondView = getChildAt(2);
        }

        if (firstView != null){
            removeView(firstView);
            contentFirst.addView(firstView);
        }

        if (secondView != null) {
            removeView(secondView);
            contentSecond.addView(secondView);
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
