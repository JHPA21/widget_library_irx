package com.ford.avsdm.corewidgetlib.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;

import com.ford.avsdm.dxpslider.R;

/**
 * DxpSlider implementation
 */
public class DxpSlider extends AppCompatSeekBar implements OnSeekBarChangeListener {
    private static final int BRIGHTNESS_STYLE = 0;
    private static final int TEMPERATURE_STYLE = 1;
    private static final int FAN_SPEED_STYLE = 2;
    private static final int VOLUME_STYLE = 3;

    private OnDxpSliderChangeListener mListener;
    private int mProgress;

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     */
    public DxpSlider(final Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context The Context the view is running in.
     * @param attrs   The attributes of the XML Button tag that is inflating the view.
     */
    public DxpSlider(final Context context, final AttributeSet attrs) {
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
    public DxpSlider(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attrArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DxpSlider, 0, 0);

        int seekbarStyle = attrArray.getInt(R.styleable.DxpSlider_sliderType, 0);
        Drawable progressBar = ContextCompat.getDrawable(getContext(), R.drawable.bar_dxp_slider_states);

        if (seekbarStyle == BRIGHTNESS_STYLE) {
            setThumb(
                setupIconThumb(
                    R.drawable.thumb_slider_brightness,
                    R.drawable.ic_brightness_states
                )
            );
        } else if (seekbarStyle == TEMPERATURE_STYLE) {
            setupGradient(progressBar, R.drawable.grad_temp_slider);
            setThumb(
                setupIconThumb(
                    R.drawable.thumb_slider_temperature,
                    R.drawable.ic_temperature_states
                )
            );
        } else if (seekbarStyle == FAN_SPEED_STYLE) {
            setupGradient(progressBar, R.drawable.grad_fan_speed);
            setThumb(
                setupIconThumb(
                    R.drawable.thumb_slider_fan_speed,
                    R.drawable.ic_fan_speed_states
                )
            );
        } else if (seekbarStyle == VOLUME_STYLE) {
            setThumb(
                    setupIconThumb(
                            R.drawable.thumb_slider_brightness,
                            R.drawable.ic_volume_states
                    )
            );
        }

        setProgressDrawable(progressBar);

        setThumbOffset(0); // To have the thumb appear in full on the bar edges.

        setOnSeekBarChangeListener(this);
    }

    /**
     * Sets the callback listener to be used to notify host apps about the changes implemented via
     * this interface.
     *
     * @param listener callback interface.
     */
    public void setOnDxpSliderChangeListener(final OnDxpSliderChangeListener listener) {
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

    /**
     * Interface that provides updates on the seekbar changes to the hosts of this widget.
     */
    public interface OnDxpSliderChangeListener {

        /**
         * This method is called whenever the user releases the slider. So it will provide the
         * updated progress to whoever implements this interface.
         *
         * @param progress updated progress.
         */
        void onProgressChangeAtSeekRelease(int progress);

        /**
         * This method is called whenever the user is sliding through seekbar and provides immediate
         * and continous updated progress to whoever implements this interface.
         *
         * @param progress updated progress.
         */
        void onProgressChangeImmediate(int progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i = getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

    private void setupGradient(Drawable progressBar, int gradientResourceId) {
        Drawable gradientDrawable = ContextCompat.getDrawable(getContext(), gradientResourceId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && progressBar != null) {
            ((LayerDrawable)((StateListDrawable) progressBar).getStateDrawable(0)).setDrawable(2, gradientDrawable);
            ((LayerDrawable)((StateListDrawable) progressBar).getStateDrawable(1)).setDrawable(2, gradientDrawable);
        }
    }

    private LayerDrawable setupIconThumb(int thumbResourceId, int iconResourceId) {
        Drawable thumb = ContextCompat.getDrawable(getContext(), thumbResourceId);
        Drawable currentIcon = ContextCompat.getDrawable(getContext(), iconResourceId);

        RotateDrawable rotatedIcon = new RotateDrawable();
        rotatedIcon.setDrawable(currentIcon);
        rotatedIcon.setFromDegrees(90f);
        rotatedIcon.setToDegrees(90f);
        rotatedIcon.setLevel(1);
        Drawable[] layers = {thumb, rotatedIcon};
        return new LayerDrawable(layers);
    }
}