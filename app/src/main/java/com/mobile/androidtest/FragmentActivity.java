package com.mobile.androidtest;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
        fragmentList.add(BaseFragment.newInstance("我的关注"));
        titleList.add("我的关注");



        FragmentPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()
                ,fragmentList,titleList);
        viewpager.setAdapter(adapter);


        LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_dividers));
        if (fragmentList.size() > 4) {
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tablayout.setTabMode(TabLayout.MODE_FIXED);
        }
        viewpager.setOffscreenPageLimit(fragmentList.size() - 1);

        tablayout.setupWithViewPager(viewpager);
//        setIndicator(tablayout,10,10);
//        reflex(tablayout);
//        tablayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(tablayout,0,0);
//            }
//        });
        showTabTextAdapteIndicator(tablayout);
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

    /** 设置tablayout指示器的长短
     *
     * @param tab
     */
    public void showTabTextAdapteIndicator(final TabLayout tab) {
        tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Class<?> tabLayout = tab.getClass();
                Field tabStrip = null;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                tabStrip.setAccessible(true);
                LinearLayout ll_tab = null;
                try {
                    ll_tab = (LinearLayout) tabStrip.get(tab);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                int maxLen = 0;
                int maxTextSize = 0;
                int tabCount = ll_tab.getChildCount();
                for (int i = 0; i < tabCount; i++) {
                    View child = ll_tab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    if (child instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) child;
                        for (int j = 0; j < ll_tab.getChildCount(); j++) {
                            if (viewGroup.getChildAt(j) instanceof TextView) {
                                TextView tabTextView = (TextView) viewGroup.getChildAt(j);
                                int length = tabTextView.getText().length();
                                maxTextSize = (int) tabTextView.getTextSize() > maxTextSize ? (int) tabTextView.getTextSize() : maxTextSize;
                                maxLen = length > maxLen ? length : maxLen;
                            }
                        }

                    }

                    int margin = (tab.getWidth() / tabCount - (maxTextSize + dip2px(2)) * maxLen) / 2 - dip2px(2);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    child.setLayoutParams(params);
                    child.invalidate();
                }


            }
        });

    }

    // 具体方法（通过反射的方式）
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.weight = 0;
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        Log.d("huang", "run: width:" + width);
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public int dip2px(int dip) {
        // dp和px的转换关系比例值
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

}
