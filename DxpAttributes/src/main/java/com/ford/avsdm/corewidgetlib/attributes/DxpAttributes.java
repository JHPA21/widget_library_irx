package com.ford.avsdm.corewidgetlib.attributes;


import com.ford.avsdm.dxpattributes.R;

/**
 * DxpAttributes
 */
public class DxpAttributes {

    /**
     * Attributes Font Types bucket
     */

    public enum ButtonType {
        SM_PILL, LG_PILL, XS_CIRCLE, SM_CIRCLE, LG_CIRCLE, SM_PILL_NO_WIDTH, MD_CIRCLE, LIGHT_SQUARE
    }

    public enum ButtonStyle {
        NEUTRAL, ACTION, ALERT, GO, INDENTED, OUTLINED, TRANSPARENT, TRANSPARENT_ALERT, POSITIVE, GREYSCALE, GREYSCALE_INVERSE
    }

    public enum FontType {
        REGULAR(R.font.ford_antenna_regular),
        MEDIUM(R.font.ford_antenna_medium),
        BOLD(R.font.ford_antenna_bold);

        private final int mResourceId;

        FontType(final int resourceId) {
            mResourceId = resourceId;
        }

        public int getResourceId() {
            return mResourceId;
        }
    }

    /**
     * Attributes Text size types bucket
     */
    public enum TextSizeType {

        XX_SMALL(R.dimen.dxp_text_view_size_xxsmall),
        X_SMALL(R.dimen.dxp_text_view_size_xsmall),
        SMALL(R.dimen.dxp_text_view_size_small),
        MEDIUM(R.dimen.dxp_text_view_size_medium),
        LARGE(R.dimen.dxp_text_view_size_large),
        X_LARGE(R.dimen.dxp_text_view_size_xlarge),
        XX_LARGE(R.dimen.dxp_text_view_size_xxlarge),
        XXX_LARGE(R.dimen.dxp_text_view_size_xxxlarge),
        XXXX_LARGE(R.dimen.dxp_text_view_size_xxxxlarge);

        private final int mResourceId;

        TextSizeType(final int resourceId) {
            mResourceId = resourceId;
        }

        public int getResourceId() {
            return mResourceId;
        }
    }

    /**
     * Attributes Text color type bucket.
     */
    public enum TextColorType {

        NEUTRAL_INVERSE(R.color.primaryInverse),
        POSITIVE(R.color.positive_001),
        BLACK_STATIC(R.color.black_static),
        WHITE_STATIC(R.color.white_static),
        HEADLINE_LIGHT(R.color.headline_light);

        private final int mResourceId;

        TextColorType(final int resourceId) {
            mResourceId = resourceId;
        }

        public int getResourceId() {
            return mResourceId;
        }
    }

    /**
     * Attributes for different text types
     */
    public enum TextType {
        DISPLAY_700(R.font.ford_antenna_regular, R.dimen.display_700_text_size, R.color.text_display),
        HEADLINE_500(R.font.ford_antenna_medium, R.dimen.headline_500_text_size, R.color.text_headline),
        HEADLINE_400(R.font.ford_antenna_bold, R.dimen.headline_400_text_size, R.color.text_headline),
        HEADLINE_300(R.font.ford_antenna_bold, R.dimen.headline_300_text_size, R.color.text_headline),
        HEADLINE_200(R.font.ford_antenna_bold, R.dimen.headline_200_text_size, R.color.text_headline),
        BODY_400(R.font.ford_antenna_regular, R.dimen.body_400_text_size, R.color.text_body),
        BODY_300(R.font.ford_antenna_regular, R.dimen.body_300_text_size, R.color.text_body),
        BODY_200(R.font.ford_antenna_regular, R.dimen.body_200_text_size, R.color.text_body),
        BODY_100(R.font.ford_antenna_regular, R.dimen.body_100_text_size, R.color.text_body),
        CAPTION_300(R.font.ford_antenna_bold, R.dimen.caption_300_text_size, R.color.text_caption),
        CAPTION_200(R.font.ford_antenna_bold, R.dimen.caption_200_text_size, R.color.text_caption),
        CAPTION_100(R.font.ford_antenna_bold, R.dimen.caption_100_text_size, R.color.text_caption),
        LABEL_500(R.font.ford_antenna_medium, R.dimen.label_500_text_size, R.color.text_label),
        LABEL_300(R.font.ford_antenna_medium, R.dimen.label_300_text_size, R.color.text_label),
        LABEL_100(R.font.ford_antenna_medium, R.dimen.label_100_text_size, R.color.text_label),
        LINK_300(R.font.ford_antenna_regular, R.dimen.link_300_text_size, R.color.text_link),
        LINK_200(R.font.ford_antenna_regular, R.dimen.link_200_text_size, R.color.text_link),
        LINK_100(R.font.ford_antenna_regular, R.dimen.link_100_text_size, R.color.text_link),
        CAPTION_400(R.font.ford_antenna_bold, R.dimen.caption_400_text_size, R.color.text_caption),
        DISPLAY_600(R.font.ford_antenna_regular, R.dimen.display_600_text_size, R.color.text_display),
        HEADLINE_350(R.font.ford_antenna_bold, R.dimen.headline_350_text_size, R.color.text_headline),
        BODY_500(R.font.ford_antenna_regular, R.dimen.body_500_text_size, R.color.text_body),
        BODY_500L(R.font.ford_antenna_medium, R.dimen.body_500_text_size, R.color.text_headline);

        static private final TextType[] mTypes = TextType.values();

        private final int mFontType;
        private final int mFontSize;
        private final int mFontColor;

        TextType(final int fontType, final int fontSize, final int fontColor) {
            mFontType = fontType;
            mFontSize = fontSize;
            mFontColor = fontColor;
        }

        static public TextType fromResId(int resId) {
            if (resId >= 0 && resId < mTypes.length) {
                return mTypes[resId];
            } else {
                return null;
            }
        }

        public int getFontType() {
            return mFontType;
        }

        public int getFontSize() {
            return mFontSize;
        }

        public int getFontColor() {
            return mFontColor;
        }
    }

    /**
     * Attributes for different Progress Indicator types
     */
    public enum ProgressIndicatorType {
        PIN_PAD, PAGE_INDICATOR;
    }
}
