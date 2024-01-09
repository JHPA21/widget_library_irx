package com.ford.avsdm.corewidgetlib.buttonbuilder;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to help build background and text selectors for buttons
 */
public class ButtonBuilder {

    private final ButtonStyle mBaseStyle;
    private final List<ButtonState> mStates = new ArrayList<>();

    /**
     * Constructor
     *
     * @param baseStyle Base button styles states should use. May be null.
     */
    public ButtonBuilder(final ButtonStyle baseStyle) {
        mBaseStyle = baseStyle;
    }

    /**
     * Adds button style for selector state
     *
     * @param state Selector states style should be associated with
     * @param style Button style
     */
    public void addState(final int[] state, final ButtonStyle style) {
        if (mBaseStyle != null) {
            style.setBaseStyle(mBaseStyle);
        }
        mStates.add(new ButtonState(state, style));
    }

    /**
     * Builds background selector
     *
     * @returns StateListDrawable instance
     */
    public StateListDrawable getBackgroundStateList() {
        final StateListDrawable stateList = new StateListDrawable();
        for (ButtonState state : mStates) {
            stateList.addState(state.getState(), state.getStyle().getBackgroundDrawable());
        }
        return stateList;
    }

    /**
     * Builds text color selector
     *
     * @returns ColorStateList instance
     */
    public ColorStateList getTextColorStateList() {
        final int numStates = mStates.size();
        final int[][] states = new int[numStates][];
        final int[] colors = new int[numStates];

        for (int i = 0; i < numStates; i++) {
            final ButtonState state = mStates.get(i);
            states[i] = state.getState();
            colors[i] = state.getStyle().getTextColor();
        }

        return new ColorStateList(states, colors);
    }

    /**
     * Holds styles for a button
     */
    public static class ButtonStyle {
        private ButtonStyle mBaseStyle;
        private Float mRadius;
        private Integer mBorderWidth;
        private Integer mBorderColor;
        private Integer mBackgroundColor;
        private Integer mTextColor;

        /**
         * Sets base style to inherit unspecified settings from
         *
         * @param baseStyle Base style
         */
        public ButtonStyle setBaseStyle(final ButtonStyle baseStyle) {
            mBaseStyle = baseStyle;
            return this;
        }

        /**
         * Set background radius
         *
         * @param radius Background radius (pixels)
         */
        public ButtonStyle setRadius(final Float radius) {
            mRadius = radius;
            return this;
        }

        /**
         * Set border width
         *
         * @param borderWidth Width of border (pixels)
         */
        public ButtonStyle setBorderWidth(final Integer borderWidth) {
            mBorderWidth = borderWidth;
            return this;
        }

        /**
         * Set border color
         *
         * @param borderColor Color of the border (argb)
         */
        public ButtonStyle setBorderColor(final Integer borderColor) {
            mBorderColor = borderColor;
            return this;
        }

        /**
         * Set background color
         *
         * @param backgroundColor Color of background (argb)
         */
        public ButtonStyle setBackgroundColor(final Integer backgroundColor) {
            mBackgroundColor = backgroundColor;
            return this;
        }

        /**
         * Set text color
         *
         * @param textColor Color of text (argb)
         */
        public ButtonStyle setTextColor(final Integer textColor) {
            mTextColor = textColor;
            return this;
        }

        /**
         * Get background radius
         * <p>
         * If not specified, tries to retrieve value from base style
         *
         * @return Background radius (pixels) or null
         */
        public Float getRadius() {
            if (mRadius == null && mBaseStyle != null) {
                return mBaseStyle.getRadius();
            }
            return mRadius;
        }

        /**
         * Get border width
         * <p>
         * If not specified, tries to retrieve value from base style
         *
         * @return Width of border (pixels) or null
         */
        public Integer getBorderWidth() {
            if (mBorderWidth == null && mBaseStyle != null) {
                return mBaseStyle.getBorderWidth();
            }
            return mBorderWidth;
        }

        /**
         * Get border color
         * <p>
         * If not specified, tries to retrieve value from base style
         *
         * @return Color of border (argb) or null
         */
        public Integer getBorderColor() {
            if (mBorderColor == null && mBaseStyle != null) {
                return mBaseStyle.getBorderColor();
            }
            return mBorderColor;
        }

        /**
         * Get background color
         * <p>
         * If not specified, tries to retrieve value from base style
         *
         * @return Color of background (argb) or null
         */
        public Integer getBackgroundColor() {
            if (mBackgroundColor == null && mBaseStyle != null) {
                return mBaseStyle.getBackgroundColor();
            }
            return mBackgroundColor;
        }

        /**
         * Get text color
         * <p>
         * If not specified, tries to retrieve value from base style
         *
         * @return Color of text (argb) or null
         */
        public Integer getTextColor() {
            if (mTextColor == null && mBaseStyle != null) {
                return mBaseStyle.getTextColor();
            }
            return mTextColor;
        }

        /**
         * Build drawable for button style
         *
         * @return Drawable generated from specified attributes
         */
        Drawable getBackgroundDrawable() {
            final GradientDrawable drawable = new GradientDrawable();

            final Float radius = getRadius();
            if (radius != null) {
                drawable.setCornerRadius(radius);
            }

            final Integer borderWidth = getBorderWidth();
            final Integer borderColor = getBorderColor();
            if (borderWidth != null && borderColor != null) {
                drawable.setStroke(borderWidth, borderColor);
            }

            final Integer backgroundColor = getBackgroundColor();
            if (backgroundColor != null) {
                drawable.setColor(backgroundColor);
            }

            return drawable;
        }
    }

    /**
     * Container to hold button state and style
     */
    public static class ButtonState {
        private final int[] mState;
        private final ButtonStyle mStyle;

        /**
         * Constructor
         *
         * @param state Selector states
         * @param style Button style
         */
        public ButtonState(final int[] state, final ButtonStyle style) {
            mState = state;
            mStyle = style;
        }

        /**
         * Get selector states
         *
         * @return Selector states
         */
        public int[] getState() {
            return mState;
        }

        /**
         * Get button style
         *
         * @return Button style
         */
        public ButtonStyle getStyle() {
            return mStyle;
        }
    }
}
