package com.mobile.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/11/12 0012 12:27
 */


public class FragmentActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_layout);
        ButterKnife.bind(this);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String title = "Fragment" + (i + 1);
            fragmentList.add(BaseFragment.newInstance(title));
            titleList.add(title);
        }
        FragmentPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()
                ,fragmentList,titleList);
        viewpager.setAdapter(adapter);

        if (fragmentList.size() > 4) {
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tablayout.setTabMode(TabLayout.MODE_FIXED);
        }
        viewpager.setOffscreenPageLimit(fragmentList.size() - 1);

        tablayout.setupWithViewPager(viewpager);
        /**
         * {@link android.support.design.widget.TabLayout#setupWithViewPager(android.support.v4.view.ViewPager, boolean, boolean)}
         * 看上边源码默认会设置一个监听mCurrentVpSelectedListener{@link TabLayout.ViewPagerOnTabSelectedListener}，
         * 如果需要走自己的监听，需要移除默认设置的监听
         */
        tablayout.clearOnTabSelectedListeners();
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
