package com.shy.zlread.weight;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.shy.zlread.fragment.SplashFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangli on 2019/5/28.
 */

public class ParallaxViewPager extends ViewPager {

    private List<Fragment> mFragments = new ArrayList<>();

    public ParallaxViewPager(Context context) {
        this(context, null);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayoutIds(FragmentManager fm, int[] ints) {
        mFragments.clear();
        for (int layoutID : ints) {
            Fragment fragment = new SplashFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(SplashFragment.LAYOUT_ID, layoutID);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        setAdapter(new ParallaxAdapter(fm));
    }

    public class ParallaxAdapter extends FragmentPagerAdapter {

        public ParallaxAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
