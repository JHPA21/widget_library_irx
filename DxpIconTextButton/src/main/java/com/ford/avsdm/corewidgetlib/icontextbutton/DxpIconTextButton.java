package com.ford.avsdm.corewidgetlib.icontextbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.ford.avsdm.corewidgetlib.userprofile.DxpUserProfile;
import com.ford.avsdm.dxpicontextbutton.R;

public class DxpIconTextButton extends ConstraintLayout implements View.OnClickListener {
    private OnCheckedChangeListener onCheckedChangeListener;
    private boolean isIconRounded = true;
    private boolean isSelected;
    private DxpUserProfile dxpUserProfile;
    private TextView tvText;
    private boolean isEnabled;

    public DxpIconTextButton(final Context context) {
        super(context);
        init(null);
    }

    public DxpIconTextButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpIconTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dxp_icon_text_button, this);
        dxpUserProfile = view.findViewById(R.id.icon_button);
        tvText = view.findViewById(R.id.text_button);
        super.setOnClickListener(this);
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DxpIconTextButton,
                    0, 0);
            try {
                isEnabled = array.getBoolean(R.styleable.DxpIconTextButton_android_enabled, true);
                String txt = array.getString(R.styleable.DxpIconTextButton_android_text);
                Drawable drawable = array.getDrawable(R.styleable.DxpIconTextButton_icon);
                isIconRounded = array.getBoolean(R.styleable.DxpIconTextButton_isIconRounded, isIconRounded);

                if (txt != null && !txt.isEmpty()) {
                    tvText.setText(txt);
                }
                if (drawable != null) {
                    if (!isIconRounded) {
                        dxpUserProfile.setPadding(dxpUserProfile.getPaddingLeft() + (int) getResources().getDimension(R.dimen.icon_btn_extra_padding_left),
                                0, 0, 0);
                        tvText.setPadding(tvText.getPaddingLeft() + (int) getResources().getDimension(R.dimen.text_btn_extra_padding_left), 0, 0, 0);
                    }
                    dxpUserProfile.setImageDrawable(drawable);
                }
                setEnabled(isEnabled);
            } finally {
                array.recycle();
            }
        }
    }

    private void setSelectedStyleButton(boolean isSelected) {
        this.isSelected = isSelected;
        activeStyle();
    }

    private void activeStyle() {
        int iconColor = isSelected ? R.color.brand_001 : R.color.primary;
        if (!isIconRounded)
            dxpUserProfile.getDrawable().setTint(ResourcesCompat.getColor(getResources(), iconColor, null));
        tvText.setTextColor(ResourcesCompat.getColor(getResources(), iconColor, null));
    }

    private void disabledStyle() {
        int iconColor = isEnabled ? R.color.primary : R.color.tertiary;
        if (!isIconRounded)
            dxpUserProfile.getDrawable().setTint(ResourcesCompat.getColor(getResources(), iconColor, null));
        tvText.setTextColor(ResourcesCompat.getColor(getResources(), iconColor, null));
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onClick(View view) {
        setSelectedStyleButton(!isSelected);
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChangeListener(isSelected);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        isEnabled = enabled;
        if (!isEnabled) {
            disabledStyle();
        } else {
            activeStyle();
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChangeListener(boolean isSelected);
    }
}
