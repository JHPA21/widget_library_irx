package com.ford.avsdm.corewidgetlib.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.dxpdivider.R;

public class DxpDividerView extends View implements View.OnClickListener {

    private boolean isSelected;
    private boolean isEnabled;
    private OnCheckedChangeListener onCheckedChangeListener;

    public DxpDividerView(Context context) {
        super(context);
        init(null);
    }

    public DxpDividerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpDividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpDivider,
                    0, 0);

            try {
                DxpDividerStyles.TypeSize typeSize =
                        DxpDividerStyles.TypeSize.values()[array.getInt(R.styleable.DxpDivider_typeSize, 0)];
                int width = getResources().getDimensionPixelSize(R.dimen.divider_width);
                setMinimumWidth(width);
                switch (typeSize) {
                    case X_SMALL:
                        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.divider_height_xs));
                        break;
                    case SMALL:
                        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.divider_height_sm));
                        break;
                    case MEDIUM:
                        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.divider_height_md));
                        break;
                    case LARGE:
                        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.divider_height_lg));
                    default:
                        break;
                }

                isEnabled = array.getBoolean(R.styleable.DxpDivider_android_enabled, true);
                setEnabled(isEnabled);

                setBackground(getBackgroundDefault());
                setClickable(true);
                setOnClickListener(this);
            } finally {
                array.recycle();
            }
        }
    }

    private void updateStyle() {
        if (isSelected) {
            setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.divider_active, null));
        } else {
            setBackground(getBackgroundDefault());
        }
    }

    private void disabledStyle() {
        if (isEnabled) {
            setBackground(getBackgroundDefault());
        } else {
            setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.divider_disabled, null));
        }
    }

    private Drawable getBackgroundDefault() {
        return ResourcesCompat.getDrawable(getResources(), R.drawable.divider_bg, null);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
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
        this.isEnabled = enabled;
        if (enabled) {
            updateStyle();
        } else {
            disabledStyle();
        }
    }

    private void setSelectedState(boolean isSelected) {
        this.isSelected = isSelected;
        setActivated(isSelected);
        updateStyle();
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
