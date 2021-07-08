package com.kssoft.lake.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kssoft.lake.R;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.delegate.FlowGroupDelegate;

public class FlowRadioGroup extends RadioGroup implements TypedView {

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private String[] valueArray;

    @AttrBind(def = 0)
    private int itemLayout = 0;

    private FlowGroupDelegate delegate;

    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new FlowGroupDelegate(this, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        delegate.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        delegate.layout(changed, l, t, r, b);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.FlowRadioGroup;
    }

    @Override
    public void initView() {
        if (titleArray != null && itemLayout != 0){

            for (int i = 0; i < titleArray.length; i++) {

                RadioButton radioButton = (RadioButton) View.inflate(getContext(), itemLayout, null);
                radioButton.setId(View.generateViewId());

                if (valueArray == null || i >= valueArray.length){
                    radioButton.setTag(String.valueOf(i));
                }else{
                    radioButton.setTag(valueArray[i]);
                }
                radioButton.setText(titleArray[i]);
                addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
