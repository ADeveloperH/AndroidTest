package com.mobile.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
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
//        for (int i = 0; i < 10; i++) {
//            String title = "Fragment" + (i + 1);
//            fragmentList.add(BaseFragment.newInstance(title));
//            titleList.add(title);
//        }
        fragmentList.add(BaseFragment.newInstance("为您服务"));
        titleList.add("为您服务");
        fragmentList.add(BaseFragment.newInstance("我的关注哈哈"));
        titleList.add("我的关注哈哈");


        FragmentPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()
                , fragmentList, titleList);
        viewpager.setAdapter(adapter);


        LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.tabBackground));
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_dividers));
        if (fragmentList.size() > 4) {
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tablayout.setTabMode(TabLayout.MODE_FIXED);
            setIndicator(tablayout);
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
                viewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置tablayout指示器的长短
     *
     * @param mTabLayout
     */
    public void setIndicator(final TabLayout mTabLayout) {
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                Class<?> tabLayout = mTabLayout.getClass();
                Field tabStrip = null;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                tabStrip.setAccessible(true);
                LinearLayout ll_tab = null;
                try {
                    ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                int tabCount = ll_tab.getChildCount();
                for (int i = 0; i < tabCount; i++) {
                    View child = ll_tab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    //当前TextView的宽度
                    float curTextViewWidth = 0;
                    if (child instanceof ViewGroup) {
                        ViewGroup tabView = (ViewGroup) child;
                        for (int j = 0; j < tabView.getChildCount(); j++) {
                            View childAt = tabView.getChildAt(j);
                            if (childAt instanceof TextView) {
                                TextView textView = ((TextView) childAt);
                                int curTextLen = textView.getText().length();
                                float cuTextSize = textView.getTextSize();
                                Log.d("huang", "showTabTextAdapteIndicator: cuTextSize:" + cuTextSize);
                                Log.d("huang", "showTabTextAdapteIndicator: curTextLen:" + curTextLen);
                                curTextViewWidth = cuTextSize * curTextLen;
                                break;
                            }
                        }
                    }

                    int margin = (int) ((mTabLayout.getWidth() / tabCount - curTextViewWidth) / 2);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        });
    }
}
