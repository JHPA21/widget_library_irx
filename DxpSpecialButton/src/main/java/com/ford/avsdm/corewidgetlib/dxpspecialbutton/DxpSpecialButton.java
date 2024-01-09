package com.ford.avsdm.corewidgetlib.dxpspecialbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DxpSpecialButton extends RelativeLayout implements View.OnClickListener {

    private ImageView imgShining;

    private OnClickListener onClickListener;

    public DxpSpecialButton(Context context) {
        super(context);
        init(null);
    }

    public DxpSpecialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpSpecialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dxp_special_text_button, this);
        imgShining = view.findViewById(R.id.img_shining);
        TextView tvText = view.findViewById(R.id.tv_text);
        setClickable(true);
        super.setOnClickListener(this);
        post(this::glareAnim);
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpSpecialButton,
                    0, 0);
            try {
                final String txt = array.getString(R.styleable.DxpSpecialButton_android_text);
                if (txt != null && !txt.isEmpty()) {
                    tvText.setText(txt);
                }
            } finally {
                array.recycle();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null) onClickListener.onClick(view);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void glareAnim() {
        Animation animation = new TranslateAnimation(0, (imgShining.getWidth() + getWidth()),0, 0);
        animation.setDuration(700);
        animation.setStartOffset(4000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        imgShining.startAnimation(animation);
    }
}
