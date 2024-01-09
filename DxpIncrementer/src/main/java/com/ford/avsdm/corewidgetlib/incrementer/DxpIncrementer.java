package com.ford.avsdm.corewidgetlib.incrementer;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ford.avsdm.corewidgetlib.iconbutton.DxpIconButton;
import com.ford.avsdm.dxpincrementer.R;

/**
 * <com.ford.avsdm.corewidgetlib.incrementer.DxpIncrementer
 *         android:id="@+id/dxpIncrementer"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         app:layout_constraintEnd_toEndOf="parent"
 *         app:layout_constraintStart_toStartOf="parent"
 *         app:layout_constraintTop_toBottomOf="@+id/textView11"
 *         app:currentValue="5"
 *         app:maxValue="15"
 *         app:minValue="5"
 *         />
 */
public class DxpIncrementer extends LinearLayout implements OnClickListener {

    private final Handler mMainHandler;

    private OnIncrementerChangeListener mListener;
    private DxpIconButton btnPlus;
    private DxpIconButton btnMinus;

    private final boolean mEnableBtn = true;
    private int mMinValue = 0;
    private int mMaxValue = 20;
    private int mCurrentValue = 0;

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     */
    public DxpIncrementer(final Context context) {
        super(context, null);
        mMainHandler = new Handler(Looper.getMainLooper());
        init(null);
    }

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     * @param attrs   The attributes of the XML Button tag that is inflating the view.
     */
    public DxpIncrementer(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs, 0);
        mMainHandler = new Handler(Looper.getMainLooper());
        init(attrs);
    }

    /**
     * Constructor
     *
     * @param context      The Context the view is running in.
     * @param attrs        The attributes of the XML Button tag that is inflating the view.
     * @param defStyleAttr The resource identifier of an attribute in the current theme whose value
     *                     is the the resource id of a style. The specified style’s attribute values
     *                     serve as default values for this view. Set this parameter to 0 to avoid
     *                     use of default values.
     */
    public DxpIncrementer(final Context context, @Nullable final AttributeSet attrs,
                          final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMainHandler = new Handler(Looper.getMainLooper());
        init(attrs);
    }

    public void init(@Nullable AttributeSet attrs) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dxp_incrementer, this);

        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpIncrementer,
                    0, 0);
            try {
                final int maxValue = array.getInteger(R.styleable.DxpIncrementer_maxValue, mMaxValue);
                if (maxValue > 0) {
                    mMaxValue = maxValue;
                }
                final int minValue = array.getInteger(R.styleable.DxpIncrementer_minValue, mMinValue);
                if (minValue > 0) {
                    mMinValue = minValue;
                }
                final int currentValue = array.getInteger(R.styleable.DxpIncrementer_currentValue, mCurrentValue);
                if (currentValue > 0) {
                    mCurrentValue = currentValue;
                }

                btnPlus = view.findViewById(R.id.btnPlus);
                btnMinus = view.findViewById(R.id.btnMinus);

                btnPlus.setOnClickListener(this);
                btnMinus.setOnClickListener(this);
                setStateListAnimator(null);
            } finally {
                array.recycle();
            }
        }
    }

    /**
     * Increments current value by 1.
     */
    private void increment() {
        handleDelta(btnPlus, (mCurrentValue < mMaxValue), +1, mMaxValue);
    }

    /**
     * Decrements current value by 1.
     */
    private void decrement() {
        handleDelta(btnMinus, (mCurrentValue > mMinValue), -1, mMinValue);
    }

    private void handleDelta(DxpIconButton buttonToDisabled, boolean deltaIsValid, int delta, int limit) {
        if (deltaIsValid) {
            mCurrentValue += delta;
            updateValue(mCurrentValue);
        }

        if (mCurrentValue == limit) {
            changeStatusButton(buttonToDisabled, !mEnableBtn);
        }

        DxpIconButton buttonToEnabled = buttonToDisabled == btnMinus ? btnPlus : btnMinus;
        changeStatusButton(buttonToEnabled, mEnableBtn);
    }

    private void changeStatusButton(DxpIconButton button, boolean enabled) {
        if (button != null) postInUiThread(() -> button.setEnabled(enabled));
    }

    /**
     * Update the value and notify the callback listener about the updated
     * value.
     *
     * @param value to set progress
     */
    private void updateValue(final int value) {
        if (mListener != null) mListener.onValueChange(value);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnPlus) {
            increment();
        } else if (v.getId() == R.id.btnMinus) {
            decrement();
        }
    }

    public void setOnIncrementerChangeListener(OnIncrementerChangeListener onIncrementerChangeListener) {
        this.mListener = onIncrementerChangeListener;
    }

    protected void postInUiThread(final Runnable runnable) {
        mMainHandler.post(runnable);
    }

    /**
     * This interface is implemented by whoever wants to listens to incrementer updates.
     */
    public interface OnIncrementerChangeListener {
        void onValueChange(int value);
    }
}
