package com.mobile.androidtest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author：hj
 * time: 2017/11/12 0012 17:08
 */


public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList;
    private final List<String> titleList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,
                                    List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titleList.get(position);
//    }
}
