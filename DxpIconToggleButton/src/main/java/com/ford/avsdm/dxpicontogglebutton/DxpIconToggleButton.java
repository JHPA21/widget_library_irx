package com.ford.avsdm.dxpicontogglebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ford.avsdm.corewidgetlib.attributes.DxpAttributes;
import com.ford.avsdm.corewidgetlib.iconbutton.DxpIconButton;

public class DxpIconToggleButton extends DxpIconButton implements View.OnClickListener {

    private boolean isSelected = false;

    private DxpAttributes.ButtonStyle unselectedButtonStyle = DxpAttributes.ButtonStyle.OUTLINED;
    private DxpAttributes.ButtonStyle selectedButtonStyle = DxpAttributes.ButtonStyle.ACTION;

    private OnCheckedChangeListener onCheckedChangeListener;
    private OnClickListener onClickListener;

    public DxpIconToggleButton(Context context) {
        super(context);
        init(null);
    }

    public DxpIconToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DxpIconToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DxpIconButton, 0, 0);
            try {
                if (getButtonType() == DxpAttributes.ButtonType.LIGHT_SQUARE) {
                    selectedButtonStyle = DxpAttributes.ButtonStyle.GREYSCALE_INVERSE;
                    unselectedButtonStyle = DxpAttributes.ButtonStyle.GREYSCALE;
                }
                else if (getButtonType() != DxpAttributes.ButtonType.LG_CIRCLE) {
                    selectedButtonStyle = DxpAttributes.ButtonStyle.GO;
                }
                updateStyle(unselectedButtonStyle);
                super.setOnClickListener(this);
            } finally {
                array.recycle();
            }
        }
    }

    @Override
    public void onClick(View view) {
        setSelectedState(!isSelected);

        if (onClickListener != null) onClickListener.onClick(view);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.onClickListener = l;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelectedState(boolean isSelected) {
        this.isSelected = isSelected;
        updateStyle((isSelected ? selectedButtonStyle : unselectedButtonStyle));
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(isSelected());
        }
    }

    /**
     * Interface definition for a callback to be invoked when a DxpIconToggleButton is checked.
     */
    public interface OnCheckedChangeListener {

        /**
         * Called when the checked state of the view has changed.
         *
         * @param isChecked The new checked state of DxpIconToggleButton.
         */
        void onCheckedChanged(boolean isChecked);
    }
}
