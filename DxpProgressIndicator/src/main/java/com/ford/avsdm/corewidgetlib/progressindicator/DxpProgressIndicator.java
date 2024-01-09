package com.ford.avsdm.corewidgetlib.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.dxpprogressindicator.R;

/**
 * <com.ford.avsdm.corewidgetlib.progressindicator.DxpProgressIndicator
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * app:maxProgress="3"
 * app:currentProgress="1"
 * app:indicatorType="pin_pad|page_indicator"
 * app:showPreviousProgress="false" />
 */

public class DxpProgressIndicator extends LinearLayout {

    private boolean mShowPreviousProgress;
    private int mMaxProgress;
    private int mCurrentProgress;

    private int mProgressDotMargin;
    private int mProgressDotSize;
    private int mProgressSelector;

    private OnProgressChangeListener mListener;

    /**
     * Constructor
     *
     * @param context
     */
    public DxpProgressIndicator(final Context context) {
        super(context);
        parseStyleAttributes(null);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public DxpProgressIndicator(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        parseStyleAttributes(attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DxpProgressIndicator(final Context context, @Nullable final AttributeSet attrs,
                                final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseStyleAttributes(attrs);
    }

    /**
     * Parse the XML Style Attributes that were supplied at time of inflation of this view.
     *
     * @param attrs
     */
    private void parseStyleAttributes(final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpProgressIndicator);
            try {
                mMaxProgress = array.getInteger(R.styleable.DxpProgressIndicator_maxProgress, 1);
                mCurrentProgress =
                        array.getInteger(R.styleable.DxpProgressIndicator_currentProgress, 0);
                mShowPreviousProgress =
                        array.getBoolean(R.styleable.DxpProgressIndicator_showPreviousProgress, true);

                DxpAttributes.ProgressIndicatorType indicatorType =
                        DxpAttributes.ProgressIndicatorType.values()[array.getInt(R.styleable.DxpProgressIndicator_indicatorType, 1)];
                setProgressIndicatorType(indicatorType);

            } finally {
                array.recycle();

                setMaxProgress(mMaxProgress);
            }
        }
    }

    /**
     * Progress Indicator type. This method is to create different types of Progress Indicato can be used in xml as mentioned below
     * app:indicatorType="pin_pad"
     *
     * @param indicatorType pin_pad | page_indicator
     */
    private void setProgressIndicatorType(final DxpAttributes.ProgressIndicatorType indicatorType) {
        switch (indicatorType) {
            case PIN_PAD:
                setPinPadIndicator();
                break;
            case PAGE_INDICATOR:
                setPageIndicator();
                break;
            default:
        }
    }

    // setting PinPad indicator attributes
    private void setPinPadIndicator() {
        mProgressDotMargin = ((int) getContext().getResources().getDimension(R.dimen.dxp_pinpad_indicator_margin));
        mProgressDotSize = ((int) getContext().getResources().getDimension(R.dimen.dxp_pinpad_indicator_circle_size));

        mProgressSelector = R.drawable.pinpad_indicator_selector;
    }

    // setting Page Indicator indicator attributes
    private void setPageIndicator() {
        mProgressDotMargin =
                getContext().getResources().getDimensionPixelSize(R.dimen.dxp_page_indicator_margin);
        mProgressDotSize =
                getContext().getResources().getDimensionPixelSize(R.dimen.dxp_page_indicator_circle_size);

        mProgressSelector = R.drawable.dxp_progress_indicator_selector;
    }

    /**
     * Set max progress and re-create the progress indicator based on new max progress.
     *
     * @param maxProgress the max progress that indicator can show.
     */
    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;

        // create progress indicator based on new provided maxProgress.
        createProgressIndicator();

        setProgress(mCurrentProgress);

        refreshView();
    }

    /**
     * Create a progress indicator using (@link ProgressIndicatorDot).
     */
    private void createProgressIndicator() {

        // Remove all added views if exists.
        removeAllViews();

        // ProgressDots are created and added horizontally (default) to the LinearLayout as childs.
        for (int i = 1; i <= mMaxProgress; i++) {

            final ProgressIndicatorDot progressIndicatorDot = new ProgressIndicatorDot(getContext());

            // set background as a drawble selector.
            progressIndicatorDot.setBackgroundResource(mProgressSelector);

            final LayoutParams params = new LayoutParams(mProgressDotSize, mProgressDotSize);
            params.setMarginStart(i == 1 ? 0 : mProgressDotMargin);

            addView(progressIndicatorDot, params);
        }
    }


    /**
     * Invalidate and re-create views. Recommended practice from Android.
     */
    private void refreshView() {
        invalidate();
        requestLayout();
    }

    /**
     * Sets listeners
     *
     * @param listener instance of class that has concrete implementation for this (@link
     *                 OnProgressChangeListener)
     */
    public void setOnProgressChangeListener(final OnProgressChangeListener listener) {
        mListener = listener;
    }

    /**
     * Set progress on indicator.
     *
     * @param progress new progress
     */
    public void setProgress(final int progress) {

        if (progress > mMaxProgress) {
            throw new IllegalArgumentException("Current progress cannot be greater than max "
                    + "progress");
        }

        mCurrentProgress = progress;

        for (int i = 0; i < getChildCount(); i++) {
            final ProgressIndicatorDot progressIndicatorDot = (ProgressIndicatorDot) getChildAt(i);

            if (mShowPreviousProgress) {
                progressIndicatorDot.setChecked(i < mCurrentProgress);
            } else {
                progressIndicatorDot.setChecked((i + 1) == mCurrentProgress);
            }
        }

        if (mListener != null) {
            mListener.onProgressChanged(mCurrentProgress);
        }
    }

    /**
     * Method to set progress dots to default indicator selector.
     */
    public void setProgressDotDefaultSelector() {
        setProgressDotsSelector(mProgressSelector);
    }

    /**
     * Method to set custom progress dots selector
     */
    public void setProgressDotsSelector(final int selector) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                final ProgressIndicatorDot progressIndicatorDot = (ProgressIndicatorDot) view;
                progressIndicatorDot.setBackgroundResource(selector);
            }
        }
        refreshView();
    }

    /**
     * Get current progress.
     *
     * @return current progress.
     */
    public int getCurrentProgress() {
        return mCurrentProgress;
    }

    /**
     * Get max progress.
     *
     * @return max progress.
     */
    public int getMaxProgress() {
        return mMaxProgress;
    }

    /**
     * To get if the progressindicator shows previous on previous progressdots.
     *
     * @return previous progress boolean status.
     */
    public boolean isPreviousProgressShown() {
        return mShowPreviousProgress;
    }

    /**
     * This interface notifies those classes (which have concrete implementation for this fo
     * interface) about the progress whenever it is changed.
     */
    public interface OnProgressChangeListener {

        /**
         * Informs about changed progress.
         *
         * @param progress updated progress.
         */
        void onProgressChanged(int progress);
    }
}
