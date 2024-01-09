package com.ford.avsdm.corewidgetlib.textview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.dxptextview.R;

/**
 * @link DxpTextView is custom view can be used as follows
 * <pre>
 * <com.ford.avsdm.corewidgetlib.textview.DxpTextView
 *      android:id="@+id/more_time"
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content"
 *      android:text="@string/need_more_time"
 *      app:textType="display_700" />
 *  </pre>
 */
public class DxpTextView extends AppCompatTextView {

    private DxpAttributes.FontType mFontType;
    private DxpAttributes.TextSizeType mTextSizeType;
    private DxpAttributes.TextColorType mTextColorType;
    private DxpAttributes.TextType mTextType = DxpAttributes.TextType.BODY_200;

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     */
    public DxpTextView(final Context context) {
        super(context);
        parseStyleAttributes(null);
    }

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     * @param attrs   The attributes of the XML Button tag that is inflating the view.
     */
    public DxpTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseStyleAttributes(attrs);
    }

    /**
     * Constructor
     *
     * @param context      The Context the view is running in.
     * @param attrs        The attributes of the XML Button tag that is inflating the view.
     * @param defStyleAttr The resource identifier of an attribute in the current theme
     *                     whose value is the the resource id of a style. The specified style’s
     *                     attribute values serve as default values for this iew. Set this parameter
     *                     to 0 to avoid use of default values.
     */
    public DxpTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseStyleAttributes(attrs);
    }

    public void updateTextColorType(@Nullable DxpAttributes.TextColorType textColorType) {
        this.mTextColorType = textColorType;
        updateStyle();
    }

    /**
     * Parse the XML Style Attributes that were supplied at time of inflation of this view.
     *
     * @param attrs
     */
    private void parseStyleAttributes(final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs,
                    R.styleable.DxpTextView, 0, 0);

            try {
                final int fontTypeAttrId = array.getInt(R.styleable.DxpTextView_fontType, -1);
                if (fontTypeAttrId != -1) {
                    mFontType = DxpAttributes.FontType.values()[fontTypeAttrId];
                }

                final int textSizeAttrId = array.getInt(R.styleable.DxpTextView_textSizeType, -1);
                if (textSizeAttrId != -1) {
                    mTextSizeType = DxpAttributes.TextSizeType.values()[textSizeAttrId];
                }

                final int textColorAttrId = array.getInt(R.styleable.DxpTextView_textColorType, -1);
                if (textColorAttrId != -1) {
                    mTextColorType = DxpAttributes.TextColorType.values()[textColorAttrId];
                }

                final int textStyleAttrId = array.getInt(R.styleable.DxpTextView_textType, -1);
                if (textStyleAttrId != -1) {
                    mTextType = DxpAttributes.TextType.fromResId(textStyleAttrId);
                }

            } finally {
                array.recycle();
            }
        }
        updateStyle();
    }

    private void updateStyle() {
        applyStyle();
        refreshView();
    }

    /**
     * Apply the use-case specified Styles
     */
    private void applyStyle() {
        final int typefaceResId =
                (mFontType == null) ? mTextType.getFontType() : mFontType.getResourceId();
        final int textSizeResId =
                (mTextSizeType == null) ? mTextType.getFontSize() : mTextSizeType.getResourceId();
        final int textColorResId =
                (mTextColorType == null) ? mTextType.getFontColor() : mTextColorType.getResourceId();

        addLetterSpacing();
        setTypeface(ResourcesCompat.getFont(getContext(), typefaceResId));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(textSizeResId));
        Resources.Theme currentTheme = getContext().getTheme();
        ColorStateList colorStateList = ResourcesCompat.getColorStateList(getResources(), textColorResId, currentTheme);
        setTextColor(colorStateList);
    }

    /**
     * Add a letter spacing depending its TextType attribute
     */
    private void addLetterSpacing() {
        switch (mTextType) {
            case HEADLINE_400:
                setLetterSpacing(0.01f);
                break;
            case LINK_300:
            case LINK_200:
            case LINK_100:
                setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            case HEADLINE_300:
            case HEADLINE_200:
            case BODY_400:
            case BODY_300:
            case BODY_200:
            case BODY_100:
                setLetterSpacing(0.02f);
                break;
            case CAPTION_300:
            case CAPTION_200:
            case CAPTION_100:
            case LABEL_500:
            case LABEL_300:
            case LABEL_100:
                setLetterSpacing(0.03f);
                break;
        }
    }

    /**
     * Invalidate and redraw layout to avoid weird Layout/Views behavior. Recommended by Android.
     */
    private void refreshView() {
        invalidate();
        requestLayout();
    }
}
