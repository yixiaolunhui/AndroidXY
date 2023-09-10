package com.yxlh.androidxy.demo.ui.cycle.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yxlh.androidxy.R;
import com.yxlh.androidxy.demo.ui.cycle.adapters.HorizontalPagerAdapter;
import com.yxlh.androidxy.demo.ui.cycle.widget.HorizontalInfiniteCycleViewPager;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class TwoWayPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cycle_fragment_two_way, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), true));
        horizontalInfiniteCycleViewPager.setScrollDuration(500);
        horizontalInfiniteCycleViewPager.setPageDuration(1000);
        horizontalInfiniteCycleViewPager.setInterpolator(null);
        horizontalInfiniteCycleViewPager.setMediumScaled(true);
        horizontalInfiniteCycleViewPager.setMaxPageScale(0.7F);
        horizontalInfiniteCycleViewPager.setMinPageScale(0.73F);
        horizontalInfiniteCycleViewPager.setCenterPageScaleOffset(50.0F);
        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(0.0F);
    }
}
