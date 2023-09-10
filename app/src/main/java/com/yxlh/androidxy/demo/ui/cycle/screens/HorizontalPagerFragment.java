package com.yxlh.androidxy.demo.ui.cycle.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yxlh.androidxy.R;
import com.yxlh.androidxy.demo.ui.cycle.adapters.HorizontalPagerAdapter;
import com.yxlh.androidxy.demo.ui.cycle.widget.HorizontalInfiniteCycleViewPager;
import com.yxlh.androidxy.demo.ui.cycle.widget.OnInfiniteCyclePageTransformListener;

public class HorizontalPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cycle_fragment_horizontal, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), false));

        horizontalInfiniteCycleViewPager.setScrollDuration(400);
        horizontalInfiniteCycleViewPager.setPageDuration(1000);
        horizontalInfiniteCycleViewPager.setInterpolator(
                AnimationUtils.loadInterpolator(getContext(), android.R.anim.overshoot_interpolator)
        );
        horizontalInfiniteCycleViewPager.setMediumScaled(false);
        horizontalInfiniteCycleViewPager.setMaxPageScale(0.8F);
        horizontalInfiniteCycleViewPager.setMinPageScale(0.5F);
        horizontalInfiniteCycleViewPager.setCenterPageScaleOffset(-130.0F);
        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(-150.0F);
        horizontalInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener(new OnInfiniteCyclePageTransformListener() {
            @Override
            public void onPreTransform(View page, float position) {

            }

            @Override
            public void onPostTransform(View page, float position) {

            }
        });

        horizontalInfiniteCycleViewPager.setCurrentItem(
                horizontalInfiniteCycleViewPager.getRealItem() + 1
        );
    }
}
