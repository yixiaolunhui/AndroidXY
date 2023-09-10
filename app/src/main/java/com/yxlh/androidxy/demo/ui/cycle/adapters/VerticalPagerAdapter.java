package com.yxlh.androidxy.demo.ui.cycle.adapters;

import static com.yxlh.androidxy.demo.ui.cycle.utils.Utils.setupItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.yxlh.androidxy.R;
import com.yxlh.androidxy.demo.ui.cycle.utils.Utils;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class VerticalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] TWO_WAY_LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.ic_fintech,
                    "Fintech"
            ),
            new Utils.LibraryObject(
                    R.drawable.cycle_ic_delivery,
                    "Delivery"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_social,
                    "Social network"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_ecommerce,
                    "E-commerce"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_wearable,
                    "Wearable"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_internet,
                    "Internet of things"
            )
    };

    private LayoutInflater mLayoutInflater;

    public VerticalPagerAdapter(final Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return TWO_WAY_LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view = mLayoutInflater.inflate(R.layout.cycle_item, container, false);

        setupItem(view, TWO_WAY_LIBRARIES[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
