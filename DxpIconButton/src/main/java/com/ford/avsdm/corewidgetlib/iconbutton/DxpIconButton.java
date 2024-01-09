package com.ford.avsdm.corewidgetlib.iconbutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.buttonbuilder.ButtonBuilder;
import com.ford.avsdm.dxpiconbutton.R;

/**
 * <p>
 * <com.ford.avsdm.corewidgetlib.iconbutton.DxpIconButton
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * android:src ="@drawable/icn_speak_to_us"
 * app:style="neutral"
 * app:type="sm_circle"/>
 */

public class DxpIconButton extends AppCompatImageButton {

    private int mWidth, mHeight;
    private DxpAttributes.ButtonType mButtonType = DxpAttributes.ButtonType.LG_CIRCLE;
    private DxpAttributes.ButtonStyle mButtonStyle = DxpAttributes.ButtonStyle.NEUTRAL;

    public DxpIconButton(final Context context) {
        super(context);
        init(null);
    }

    public DxpIconButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpIconButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void updateStyle(DxpAttributes.ButtonStyle buttonStyle) {
        this.mButtonStyle = buttonStyle;
        styleButton();
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpIconButton,
                    0, 0);

            try {
                final int typeAttrId = array.getInt(R.styleable.DxpIconButton_type, -1);
                if (typeAttrId != -1) {
                    mButtonType = DxpAttributes.ButtonType.values()[typeAttrId];
                }

                final int styleAttrId = array.getInt(R.styleable.DxpIconButton_style, -1);
                if (styleAttrId != -1) {
                    mButtonStyle = DxpAttributes.ButtonStyle.values()[styleAttrId];
                }
            } finally {
                array.recycle();
            }
        }
        styleButton();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int desiredWidth = mWidth;
        final int desiredHeight = mHeight;

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        final int width, height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    private void styleButton() {
        final Resources res = getResources();

        final ButtonBuilder.ButtonStyle baseStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle defaultStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle pressedStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle disabledStyle = new ButtonBuilder.ButtonStyle();

        setUpBaseStyle(res, baseStyle);
        setUpButtonStyle(res, baseStyle, defaultStyle, pressedStyle, disabledStyle);

        final int[] defaultState = {android.R.attr.state_enabled, -android.R.attr.state_pressed};
        final int[] pressedState = {android.R.attr.state_enabled, android.R.attr.state_pressed};
        final int[] disabledState = {-android.R.attr.state_enabled};

        final ButtonBuilder buttonBuilder = new ButtonBuilder(baseStyle);
        buttonBuilder.addState(defaultState, defaultStyle);
        buttonBuilder.addState(pressedState, pressedStyle);
        buttonBuilder.addState(disabledState, disabledStyle);

        setBackground(buttonBuilder.getBackgroundStateList());
        setClickable(true);
    }

    private void setUpBaseStyle(Resources res, ButtonBuilder.ButtonStyle baseStyle) {
        switch (mButtonType) {
            case LG_CIRCLE:
                mWidth = res.getDimensionPixelSize(R.dimen.circle_btn_large);
                mHeight = res.getDimensionPixelSize(R.dimen.circle_btn_large);
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_large) / 2.0f);
                break;
            case MD_CIRCLE:
                mWidth = res.getDimensionPixelSize(R.dimen.circle_btn_medium);
                mHeight = res.getDimensionPixelSize(R.dimen.circle_btn_medium);
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_medium) / 2.0f);
                break;
            case SM_CIRCLE:
                mWidth = res.getDimensionPixelSize(R.dimen.circle_btn_small);
                mHeight = res.getDimensionPixelSize(R.dimen.circle_btn_small);
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_small) / 2.0f);
                break;
            case XS_CIRCLE:
                mWidth = res.getDimensionPixelSize(R.dimen.circle_btn_xsmall);
                mHeight = res.getDimensionPixelSize(R.dimen.circle_btn_xsmall);
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_xsmall) / 2.0f);
                break;
            case LIGHT_SQUARE:
                mWidth = res.getDimensionPixelSize(R.dimen.square_btn_width);
                mHeight = res.getDimensionPixelSize(R.dimen.square_btn_height);
                baseStyle.setRadius(res.getDimension(R.dimen.square_btn_radius));
                break;
            default:
                break;
        }
    }

    private void setUpButtonStyle(Resources res, ButtonBuilder.ButtonStyle baseStyle,
                                  ButtonBuilder.ButtonStyle defaultStyle,
                                  ButtonBuilder.ButtonStyle pressedStyle, ButtonBuilder.ButtonStyle disabledStyle) {
        disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_btn_disabled, null));
        switch (mButtonStyle) {
            case NEUTRAL:
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_btn_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_btn_pressed, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.content_color_states, null));
                break;
            case OUTLINED:
                baseStyle.setBorderWidth(res.getDimensionPixelSize(R.dimen.circle_border_btn_width))
                        .setBorderColor(ResourcesCompat.getColor(res, R.color.circle_border_btn_width, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_outlined_btn_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_btn_pressed, null));
                disabledStyle.setBorderColor(ResourcesCompat.getColor(res, R.color.circle_outlined_btn_disabled, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.content_color_states, null));
                break;
            case ACTION:
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_action_btn_default, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.circle_icon_action, null));
                break;
            case GO:
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.circle_go_btn_default, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.circle_icon_go, null));
                break;
            case GREYSCALE:
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.white_static, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.grey_scale_icon, null));
                break;
            case GREYSCALE_INVERSE:
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.grey_scale_icon, null));
                setImageTintList(ResourcesCompat.getColorStateList(res, R.color.grey_scale_white, null));
                break;
            default:
                break;
        }
    }

    protected DxpAttributes.ButtonType getButtonType() {
        return mButtonType;
    }
}
