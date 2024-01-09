package com.ford.avsdm.corewidgetlib.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

/**
 * Create a ProgressIndicatorDot that has the checkable interface inherited from the compound
 * button. This exposed checked/unchecked states to UiAutomator - for all the Dots on the
 * progressIndicator
 *
 * @param progressDotSize the size of the progress dot.
 * @param progressDotMargin margin gap between progress dots.
 */

/**
 * @see <a href="https://www.eesewiki.ford.com/display/taas/DXPProgressIndicator">https://www.eesewiki.ford.com/</a>
 */

public class ProgressIndicatorDot extends CompoundButton {

    /**
     * Constructor
     *
     * @param context
     */
    public ProgressIndicatorDot(final Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public ProgressIndicatorDot(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */

    public ProgressIndicatorDot(final Context context, @Nullable final AttributeSet attrs,
                                final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
