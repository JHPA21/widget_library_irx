package com.ford.avsdm.corewidgetlib.textbutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.buttonbuilder.ButtonBuilder;
import com.ford.avsdm.dxptextbutton.R;

/**
 * <pre>
 * &lt;com.ford.avsdm.corewidgetlib.textbutton.DxpTextButton
 *     android:layout_width="@dimen/xyz"
 *     android:layout_height="@dimen/xyz"
 *     android:gravity="center"
 *     android:text="@string/xyz"
 *     app:style="primary"
 *     app:type="button" /&gt;
 * </pre>
 */
public class DxpTextButton extends AppCompatButton {

    private DxpAttributes.ButtonType mButtonType = DxpAttributes.ButtonType.SM_PILL_NO_WIDTH;
    private DxpAttributes.ButtonStyle mButtonStyle = DxpAttributes.ButtonStyle.NEUTRAL;
    private DxpAttributes.FontType mFontType = DxpAttributes.FontType.REGULAR;
    private DxpAttributes.TextType mTextType = DxpAttributes.TextType.LABEL_300;

    public DxpTextButton(final Context context) {
        super(context);
        init(null);
    }

    public DxpTextButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpTextButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpTextButton,
                    0, 0);

            try {
                final int typeAttrId = array.getInt(R.styleable.DxpTextButton_type, -1);
                if (typeAttrId != -1) {
                    mButtonType = DxpAttributes.ButtonType.values()[typeAttrId];
                }

                final int styleAttrId = array.getInt(R.styleable.DxpTextButton_style, -1);
                if (styleAttrId != -1) {
                    mButtonStyle = DxpAttributes.ButtonStyle.values()[styleAttrId];
                }

                final int fontTypeAttrId = array.getInt(R.styleable.DxpTextButton_fontType, -1);
                if (fontTypeAttrId != -1) {
                    mFontType = DxpAttributes.FontType.values()[fontTypeAttrId];
                }

                final int textTypeId = array.getInt(R.styleable.DxpTextButton_textType, -1);
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

        final ButtonBuilder.ButtonStyle baseStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle defaultStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle pressedStyle = new ButtonBuilder.ButtonStyle();
        final ButtonBuilder.ButtonStyle disabledStyle = new ButtonBuilder.ButtonStyle();

        float textSize = mTextType.getFontSize();

        switch (mButtonType) {
            case LG_PILL:
                mFontType = DxpAttributes.FontType.BOLD;
                textSize = res.getDimension(R.dimen.pill_btn_large_text_size);
                setWidth(res.getDimensionPixelSize(R.dimen.pill_btn_large_width));
                setHeight(res.getDimensionPixelSize(R.dimen.pill_btn_large_height));
                baseStyle.setRadius(res.getDimension(R.dimen.pill_btn_large_height) / 2.0f);
                break;
            case SM_PILL:
                textSize = res.getDimension(R.dimen.pill_btn_small_text_size);
                setWidth(res.getDimensionPixelSize(R.dimen.pill_btn_small_width));
                setHeight(res.getDimensionPixelSize(R.dimen.pill_btn_small_height));
                baseStyle.setRadius(res.getDimension(R.dimen.pill_btn_small_height) / 2.0f);
                break;
            case SM_PILL_NO_WIDTH:
                mFontType = DxpAttributes.FontType.MEDIUM;
                textSize = res.getDimension(mTextType.getFontSize());
                setHeight(res.getDimensionPixelSize(R.dimen.button_btn_height));
                setPadding(
                        res.getDimensionPixelSize(R.dimen.button_padding_left),
                        res.getDimensionPixelSize(R.dimen.button_padding_top),
                        res.getDimensionPixelSize(R.dimen.button_padding_right),
                        res.getDimensionPixelSize(R.dimen.button_padding_bottom)
                );
                baseStyle.setRadius(res.getDimension(R.dimen.button_btn_radius));
                break;
            case LG_CIRCLE:
                textSize = res.getDimension(R.dimen.circle_btn_large_text_size);
                setWidth(res.getDimensionPixelSize(R.dimen.circle_btn_large_width));
                setHeight(res.getDimensionPixelSize(R.dimen.circle_btn_large_height));
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_large_height) / 2.0f);
                break;
            case SM_CIRCLE:
                textSize = res.getDimension(R.dimen.circle_btn_small_text_size);
                setWidth(res.getDimensionPixelSize(R.dimen.circle_btn_small_width));
                setHeight(res.getDimensionPixelSize(R.dimen.circle_btn_small_height));
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_small_height) / 2.0f);
                break;
            case XS_CIRCLE:
                textSize = res.getDimension(R.dimen.circle_btn_xsmall_text_size);
                setWidth(res.getDimensionPixelSize(R.dimen.circle_btn_xsmall_width));
                setHeight(res.getDimensionPixelSize(R.dimen.circle_btn_xsmall_height));
                baseStyle.setRadius(res.getDimension(R.dimen.circle_btn_xsmall_height) / 2.0f);
            default:
                break;
        }

        switch (mButtonStyle) {
            case NEUTRAL:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.neutral_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.neutral_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.neutral_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.neutral_button_disabled, null));
                break;
            case TRANSPARENT:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.transparent_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.transparent_button_text_disabled, null));
                break;
            case ACTION:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.action_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.action_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.action_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.action_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.action_button_text_disabled, null));
                break;
            case GO:
                textSize = res.getDimension(R.dimen.headline_200_text_size);
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.go_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.go_button_default, null))
                        .setBorderWidth(res.getDimensionPixelSize(R.dimen.border_width))
                        .setBorderColor(ResourcesCompat.getColor(res, R.color.go_button_border, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.go_button_pressed, null))
                        .setBorderWidth(res.getDimensionPixelSize(R.dimen.border_width))
                        .setBorderColor(ResourcesCompat.getColor(res, R.color.go_button_border, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.go_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.go_button_text_disabled, null));
                break;
            case ALERT:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.alert_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.alert_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.alert_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.alert_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.alert_button_text_disabled, null));
                break;
            case TRANSPARENT_ALERT:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.transparent_alert_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_alert_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_alert_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.transparent_alert_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.transparent_alert_button_text_disabled, null));
                break;
            case POSITIVE:
                baseStyle.setTextColor(ResourcesCompat.getColor(res, R.color.positive_button_text, null));
                defaultStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.positive_button_default, null));
                pressedStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.positive_button_pressed, null));
                disabledStyle.setBackgroundColor(ResourcesCompat.getColor(res, R.color.positive_button_disabled, null))
                        .setTextColor(ResourcesCompat.getColor(res, R.color.positive_button_text_disabled, null));
            default:
                break;
        }

        final int[] defaultState = {android.R.attr.state_enabled, -android.R.attr.state_pressed};
        final int[] pressedState = {android.R.attr.state_enabled, android.R.attr.state_pressed};
        final int[] disabledState = {-android.R.attr.state_enabled};

        final ButtonBuilder buttonBuilder = new ButtonBuilder(baseStyle);
        buttonBuilder.addState(defaultState, defaultStyle);
        buttonBuilder.addState(pressedState, pressedStyle);
        buttonBuilder.addState(disabledState, disabledStyle);

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setAllCaps(false);
        setTypeface(ResourcesCompat.getFont(getContext(), mFontType.getResourceId()));
        setTextColor(buttonBuilder.getTextColorStateList());
        setBackground(buttonBuilder.getBackgroundStateList());
        setStateListAnimator(null); //remove shadows
    }
}
