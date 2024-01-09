package com.ford.avsdm.corewidgetlib.texttoggle;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.togglebutton.DxpToggleButton;
import com.ford.avsdm.dxptexttoggle.R;

/**
 * <com.ford.avsdm.corewidgetlib.texttoggle.DxpTextToggle
 * android:id="@+id/toggle"
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * app:textLeft="Climate"
 * app:textCenter="Door"
 * app:textRight="Display" />
 */

public class DxpTextToggle extends LinearLayout {

    @Nullable
    private OnDxpTextToggleCheckedChangeListener onDxpTextToggleCheckedChangeListener;

    private DxpToggleButton mTxtToggleButtonLeft;
    private DxpToggleButton mTxtToggleButtonCenter;
    private DxpToggleButton mTxtToggleButtonRight;

    private String mTextLeft;
    private String mTextCenter;
    private String mTextRight;

    private DxpAttributes.FontType mFontType = DxpAttributes.FontType.REGULAR;
    private DxpAttributes.TextType mTextType = DxpAttributes.TextType.LABEL_300;

    public DxpTextToggle(@NonNull final Context context) {
        super(context);
        init(null);
    }

    public DxpTextToggle(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpTextToggle(@NonNull final Context context, @Nullable final AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dxp_toggle_text, this, true);

        if (attrs != null) {
            TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpTextToggle,
                    0, 0);
            try {
                mTextLeft = array.getString(R.styleable.DxpTextToggle_textLeft);
                mTextCenter = array.getString(R.styleable.DxpTextToggle_textCenter);
                mTextRight = array.getString(R.styleable.DxpTextToggle_textRight);
                boolean isHideCenterButton = array.getBoolean(R.styleable.DxpTextToggle_hideCenterButton, false);

                final int fontTypeAttrId = array.getInt(com.ford.avsdm.dxptogglebutton.R.styleable.DxpTextToggle_fontType, -1);
                if (fontTypeAttrId != -1) {
                    mFontType = DxpAttributes.FontType.values()[fontTypeAttrId];
                }

                final int textTypeId = array.getInt(com.ford.avsdm.dxptogglebutton.R.styleable.DxpTextToggle_textType, -1);
                if (textTypeId != -1) {
                    mTextType = DxpAttributes.TextType.values()[textTypeId];
                }

                mTxtToggleButtonLeft = view.findViewById(R.id.txt_toggle_button_left);
                mTxtToggleButtonCenter = view.findViewById(R.id.txt_toggle_button_center);
                mTxtToggleButtonRight = view.findViewById(R.id.txt_toggle_button_right);

                if (isHideCenterButton) mTxtToggleButtonCenter.setVisibility(View.GONE);
            } finally {
                array.recycle();
            }
        }
        updateToggles();
    }

    private void updateToggles() {
        applyText();
        refreshView();
    }

    private void applyText() {
        final Resources res = getResources();
        final String txtToggleLeft =
                (!TextUtils.isEmpty(mTextLeft) ? mTextLeft : res.getString(R.string.climate));

        final String txtToggleCenter =
                (!TextUtils.isEmpty(mTextCenter) ? mTextCenter : res.getString(R.string.doors));

        final String txtToggleRight =
                (!TextUtils.isEmpty(mTextRight) ? mTextRight : res.getString(R.string.display));

        mTxtToggleButtonLeft.setText(txtToggleLeft);
        mTxtToggleButtonCenter.setText(txtToggleCenter);
        mTxtToggleButtonRight.setText(txtToggleRight);

        mTxtToggleButtonLeft.setTextType(mTextType);
        mTxtToggleButtonCenter.setTextType(mTextType);
        mTxtToggleButtonRight.setTextType(mTextType);

        mTxtToggleButtonLeft.setFontType(mFontType);
        mTxtToggleButtonCenter.setFontType(mFontType);
        mTxtToggleButtonRight.setFontType(mFontType);

        mTxtToggleButtonLeft.setOnClickListener(this::setActiveTextViewToggle);
        mTxtToggleButtonCenter.setOnClickListener(this::setActiveTextViewToggle);
        mTxtToggleButtonRight.setOnClickListener(this::setActiveTextViewToggle);

        mTxtToggleButtonLeft.performClick();
    }

    /**
     * It will put active the toggle clicked and uncheck the others toggles
     *
     * @param v view clicked by the users
     */
    private void setActiveTextViewToggle(View v) {
        boolean isTxtViewTextLeft = mTxtToggleButtonLeft == v;
        boolean isTxtViewTextCenter = mTxtToggleButtonCenter == v;
        boolean isTxtViewTextRight = mTxtToggleButtonRight == v;
        mTxtToggleButtonLeft.setChecked(isTxtViewTextLeft);
        mTxtToggleButtonCenter.setChecked(isTxtViewTextCenter);
        mTxtToggleButtonRight.setChecked(isTxtViewTextRight);
        if (onDxpTextToggleCheckedChangeListener != null) {
            onDxpTextToggleCheckedChangeListener
                    .onCheckedChanged(isTxtViewTextLeft, isTxtViewTextCenter, isTxtViewTextRight);
        }
    }

    /**
     * Register a callback to be invoked when the checked state of this DxpTextToggle view
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnDxpTextToggleChangeListener(final OnDxpTextToggleCheckedChangeListener listener) {
        onDxpTextToggleCheckedChangeListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when a DxpTextToggle is checked.
     */
    public interface OnDxpTextToggleCheckedChangeListener {

        /**
         * Called when the checked state of the view has changed.
         *
         * @param isRightChecked The new checked state of DxpTextToggle.
         */
        void onCheckedChanged(boolean isLeftChecked, boolean isCenterChecked, boolean isRightChecked);
    }

    /**
     * Invalidate and redraw layout to avoid weird Layout/Views behavior. Recommended by Android.
     */
    private void refreshView() {
        invalidate();
        requestLayout();
    }

    public void setTextLeft(String textLeft) {
        mTextLeft = textLeft;
        updateToggles();
    }

    public String getTextLeft() {
        return mTxtToggleButtonLeft.getText().toString();
    }

    public void setTextCenter(String textCenter) {
        mTextCenter = textCenter;
        updateToggles();
    }

    public String getTextCenter() {
        return mTxtToggleButtonCenter.getText().toString();
    }

    public void setTextRight(String textRight) {
        mTextRight = textRight;
        updateToggles();
    }

    public String getTextRight() {
        return mTxtToggleButtonRight.getText().toString();
    }

   public void setFontType(DxpAttributes.FontType fontType) {
        mFontType = fontType;
        updateToggles();
    }

    public void setTextType(DxpAttributes.TextType textType) {
        mTextType = textType;
        updateToggles();
    }
}
