package com.ford.avsdm.corewidgetlib.seekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;

import com.ford.avsdm.dxpseekbar.R;

/**
 * DxpSeekBar implementation
 */
public class DxpSeekBar extends AppCompatSeekBar implements OnSeekBarChangeListener {
    private OnDxpChangeListener mListener;
    private int mProgress;

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     */
    public DxpSeekBar(final Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     * @param attrs   The attributes of the XML Button tag that is inflating the view.
     */
    public DxpSeekBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
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
    public DxpSeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Setups up the seekbar.
     */
    private void init() {
        setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bar_dxp_seekbar_states));
        setThumb(ContextCompat.getDrawable(getContext(), R.drawable.thumb_dxp_seekbar_states));
        setThumbOffset(0); // To have the thumb appear in full on the bar edges.
        setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.padding_left), 0,
                getContext().getResources().getDimensionPixelSize(R.dimen.padding_right),0);
        setOnSeekBarChangeListener(this);
    }

    /**
     * Sets the callback listener to be used to notify host apps about the changes implemented via
     * this interface.
     *
     * @param listener callback interface.
     */
    public void setDxpChangeListener(final OnDxpChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress,
                                  final boolean fromUser) {
        mProgress = progress;

        if (mListener == null) {
            return;
        }

        mListener.onProgressChangeImmediate(mProgress);
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
        if (mListener == null) {
            return;
        }

        mListener.onProgressChangeAtSeekRelease(mProgress);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled())  return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setThumb(ContextCompat.getDrawable(getContext(), R.drawable.thumb_dxp_seekbar_pressed));
                break;
            case MotionEvent.ACTION_UP:
                setThumb(ContextCompat.getDrawable(getContext(), R.drawable.thumb_dxp_seekbar));
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Interface that provides updates on the seekbar changes to the hosts of this widget.
     */
    public interface OnDxpChangeListener {

        /**
         * This method is called whenever the user releases the slider. So it will provide the
         * updated progress to whoever implements this interface.
         *
         * @param progress updated progress.
         */
        void onProgressChangeAtSeekRelease(int progress);

        /**
         * This method is called whenever the user is sliding through seekbar and provides immediate
         * and continuous updated progress to whoever implements this interface.
         *
         * @param progress updated progress.
         */
        void onProgressChangeImmediate(int progress);
    }
}