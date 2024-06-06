package com.yxlh.androidxy.demo.ui.textview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

/**
 * @author zwl
 * @date on 2024/6/5
 */
public class ExpandableTextExt {

    /**
     * SpannableStringBuilder 添加ImageSpan
     *
     * @param spannableStringBuilder
     * @param drawable
     */
    public static void addImageSpan(SpannableStringBuilder spannableStringBuilder, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable);
        int start = spannableStringBuilder.length();
        spannableStringBuilder.append(" ");
        spannableStringBuilder.setSpan(imageSpan, start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    public static void addImageSpan(SpannableStringBuilder spannableStringBuilder, Drawable drawable, OnImageSpanClickListener listener) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable);
        int start = spannableStringBuilder.length();
        spannableStringBuilder.append(" ");
        spannableStringBuilder.setSpan(imageSpan, start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onImageSpanClick();
                }
            }
        };
        spannableStringBuilder.setSpan(clickableSpan, start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public interface OnImageSpanClickListener {
        void onImageSpanClick();
    }

    /**
     * 获取ImageSpan宽度
     *
     * @param context
     * @param drawableResId
     * @return
     */
    public static int getImageSpanWidth(Context context, int drawableResId) {
        Drawable drawable = context.getResources().getDrawable(drawableResId);
        return getImageSpanWidth(context, drawable);
    }

    /**
     * 获取ImageSpan宽度
     *
     * @param context
     * @param drawable
     * @return
     */
    public static int getImageSpanWidth(Context context, Drawable drawable) {
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" ");
        spannableStringBuilder.setSpan(imageSpan, 0, 1, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable.getIntrinsicWidth();
    }


}
