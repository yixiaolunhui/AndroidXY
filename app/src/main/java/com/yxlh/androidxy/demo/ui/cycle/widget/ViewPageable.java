package com.yxlh.androidxy.demo.ui.cycle.widget;

import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

interface ViewPageable {

    boolean hasWindowFocus();

    int getCurrentItem();

    int getChildCount();

    View getChildAt(final int index);

    PagerAdapter getAdapter();

    boolean post(final Runnable runnable);

    void addOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener);

    void setClipChildren(final boolean clipChildren);

    void setDrawingCacheEnabled(final boolean drawingCacheEnabled);

    void setWillNotCacheDrawing(final boolean willNotCacheDrawing);

    void setOverScrollMode(final int overScrollMode);

    void setCurrentItem(final int item);

    void setPageTransformer(final boolean reverseDrawingOrder, final ViewPager.PageTransformer transformer);

    void setPageMargin(final int pageMargin);

    void setOffscreenPageLimit(final int offscreenPageLimit);

    boolean isFakeDragging();

    boolean beginFakeDrag();

    void fakeDragBy(final float dragBy);

    void endFakeDrag();

}