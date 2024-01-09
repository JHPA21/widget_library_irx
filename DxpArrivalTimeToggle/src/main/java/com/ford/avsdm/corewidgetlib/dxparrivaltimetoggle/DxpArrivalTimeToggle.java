package com.ford.avsdm.corewidgetlib.dxparrivaltimetoggle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.textview.DxpTextView;

public class DxpArrivalTimeToggle extends ConstraintLayout implements View.OnClickListener {

    private ImageView imgClock;
    private DxpTextView tvLeft;
    private DxpTextView tvRight;
    private View divider;

    private boolean isSelected;
    private boolean isEnabled;
    private boolean isListener;

    private OnCheckedChangeListener onCheckedChangeListener;

    public DxpArrivalTimeToggle(Context context) {
        super(context);
        init(null);
    }

    public DxpArrivalTimeToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpArrivalTimeToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dxp_arrival_time, this);
        imgClock = view.findViewById(R.id.img_clock);
        tvLeft = view.findViewById(R.id.tv_left);
        tvRight = view.findViewById(R.id.tv_right);
        divider = view.findViewById(R.id.divider);
        setClickable(true);
        setOnClickListener(this);
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs,
                    R.styleable.DxpArrivalTimeToggle,
                    0,0);

            final String txtLeft = array.getString(R.styleable.DxpArrivalTimeToggle_textLeft);
            if (txtLeft != null && !txtLeft.isEmpty()) {
                tvLeft.setText(txtLeft);
            }

            final String txtRight = array.getString(R.styleable.DxpArrivalTimeToggle_textRight);
            if (txtLeft != null && !txtLeft.isEmpty()) {
                tvRight.setText(txtRight);
            }

            isEnabled = array.getBoolean(R.styleable.DxpArrivalTimeToggle_android_enabled, true);
            setEnabled(isEnabled);
        }
    }

    private void updateStyle() {
        if (isListener) {
            setActivated(isSelected);
            int styleDrawable = isSelected ? R.drawable.arrival_time_active_bg : R.drawable.arrival_time_selector;
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), styleDrawable, null);
            setBackground(drawable);

            if (imgClock != null) {
                int iconColor = isSelected ? R.color.arrival_time_icon_active : R.color.arrival_time_icon_default;
                imgClock.setImageTintList(ResourcesCompat.getColorStateList(getResources(), iconColor, null));
            }
            int dividerColor = isSelected ? R.color.arrival_time_divider_active : R.color.arrival_time_divider;
            divider.setBackgroundColor(ResourcesCompat.getColor(getResources(), dividerColor, null));
            int textColor = isSelected ? R.color.arrival_time_text_active : R.color.arrival_time_text_default;
            tvLeft.setTextColor(ResourcesCompat.getColor(getResources(), textColor, null));
            tvRight.setTextColor(ResourcesCompat.getColor(getResources(), textColor, null));
        } else {
            styleDefault();
        }
    }

    private void disabledStyle() {
        int styleDrawable = !isEnabled ? R.drawable.arrival_time_disabled_bg : R.drawable.arrival_time_selector;
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), styleDrawable, null);
        setBackground(drawable);
        int iconColor = !isEnabled ? R.color.arrival_time_icon_disabled : R.color.arrival_time_icon_default;
        if (imgClock != null) {
            imgClock.setImageTintList(ResourcesCompat.getColorStateList(getResources(), iconColor, null));
        }
        divider.setBackgroundColor(ResourcesCompat.getColor(getResources(), iconColor, null));
        tvLeft.setTextColor(ResourcesCompat.getColor(getResources(), R.color.arrival_time_text_disabled, null));
        tvRight.setTextColor(ResourcesCompat.getColor(getResources(), R.color.arrival_time_text_disabled, null));
        refreshDrawableState();
        requestLayout();
    }

    private void styleDefault() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.arrival_time_default_bg, null);
        setBackground(drawable);
        if (imgClock != null) {
            int iconColor = R.color.arrival_time_icon_default;
            imgClock.setImageTintList(ResourcesCompat.getColorStateList(getResources(), iconColor, null));
        }
    }

    public void setTextLeft(String text) {
        tvLeft.setText(text);
    }

    public void setTextRight(String text) {
        tvRight.setText(text);
    }

    public void setOnCheckedChangeListener(
            OnCheckedChangeListener onCheckedChangeListener) {
        isListener = true;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onClick(View view) {
        if (onCheckedChangeListener != null) {
            setSelectedState(!isSelected);
            onCheckedChangeListener.onCheckedChanged(isSelected);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        isEnabled = enabled;
        if (!isEnabled) {
            disabledStyle();
        } else {
            updateStyle();
        }
    }

    private void setSelectedState(boolean isSelected) {
        this.isSelected = isSelected;
        updateStyle();
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
