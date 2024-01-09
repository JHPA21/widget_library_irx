package com.ford.avsdm.corewidgetlib.togglebutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.buttonbuilder.ButtonBuilder;
import com.ford.avsdm.dxptogglebutton.R;

/**
 * <com.ford.avsdm.corewidgetlib.togglebutton.DxpToggleButton
 * android:id="@+id/dxpToggleButton"
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * android:text="Cancel"/>
 */
public class DxpToggleButton extends CompoundButton {

    private DxpAttributes.FontType mFontType = DxpAttributes.FontType.REGULAR;
    private DxpAttributes.TextType mTextType = DxpAttributes.TextType.LABEL_300;

    public DxpToggleButton(Context context) {
        super(context);
        init(null);
    }

    public DxpToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpToggleButton,
                    0, 0);

            try {
                final int fontTypeAttrId = array.getInt(R.styleable.DxpToggleButton_fontType, -1);
                if (fontTypeAttrId != -1) {
                    mFontType = DxpAttributes.FontType.values()[fontTypeAttrId];
                }

                final int textTypeId = array.getInt(R.styleable.DxpToggleButton_textType, -1);
                if (textTypeId != -1) {
                    mTextType = DxpAttributes.TextType.values()[textTypeId];
                }
            } finally {
                array.recycle();
            }
        }
        styleButton();
    }

    private void styleButton() {
        final Resources res = getResources();
        float textSize = res.getDimension(mTextType.getFontSize());

        final ButtonBuilder.ButtonStyle baseStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle defaultStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle pressedStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle selectedStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle disabledStyle = new ButtonBuilder.ButtonStyle();

        setPadding(
                res.getDimensionPixelSize(R.dimen.button_padding_left),
                res.getDimensionPixelSize(R.dimen.button_padding_top),
                res.getDimensionPixelSize(R.dimen.button_padding_right),
                res.getDimensionPixelSize(R.dimen.button_padding_bottom)
        );
        baseStyle.setRadius(res.getDimension(R.dimen.pill_btn_height));

        defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.pill_btn_default, null))
                .setTextColor(ResourcesCompat.getColor(res, R.color.pill_btn_text_default, null));

        pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.pill_btn_pressed, null))
            .setTextColor(ResourcesCompat.getColor(res, R.color.pill_btn_text_default, null));

        selectedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.pill_btn_selected, null))
                .setTextColor(ResourcesCompat.getColor(res, R.color.pill_btn_text_selected, null));

        disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.pill_btn_disabled, null))
                .setTextColor(ResourcesCompat.getColor(res, R.color.pill_btn_text_disabled, null));

        final int[] defaultState = {android.R.attr.state_enabled, -android.R.attr.state_checked};
        final int[] pressedState = {android.R.attr.state_enabled, android.R.attr.state_pressed};
        final int[] selectedState = {android.R.attr.state_enabled, android.R.attr.state_checked};
        final int[] disabledState = {-android.R.attr.state_enabled};

        final ButtonBuilder buttonBuilder = new ButtonBuilder(baseStyle);
        buttonBuilder.addState(selectedState, selectedStyle);
        buttonBuilder.addState(pressedState, pressedStyle);
        buttonBuilder.addState(defaultState, defaultStyle);
        buttonBuilder.addState(disabledState, disabledStyle);

        setClickable(true);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setTextColor(buttonBuilder.getTextColorStateList());
        setTypeface(ResourcesCompat.getFont(getContext(), mFontType.getResourceId()));
        setBackground(buttonBuilder.getBackgroundStateList());
        setStateListAnimator(null);
    }

    private void refreshView() {
        invalidate();
        requestLayout();
    }

    private void updateStyle() {
        styleButton();
        refreshView();
    }

    public void setFontType(DxpAttributes.FontType fontType) {
        mFontType = fontType;
        updateStyle();
    }

    public void setTextType(DxpAttributes.TextType textType) {
        mTextType = textType;
        updateStyle();
    }
}
