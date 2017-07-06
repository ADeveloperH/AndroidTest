package com.mobile.androidtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.mobile.androidtest.fragment.SimpleTextFragment;

import java.util.List;

/**
 * author：hj
 * time: 2017/7/2 0002 18:25
 */


public class MainFragmentAdapter extends FragmentPagerAdapter {
    private String TAG = "hhh";
    private String Name = "MainFragmentAdapter";
    private final FragmentManager fm;
    private final List<Fragment> fragmentList;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        Log.d(TAG, Name + ":MainFragmentAdapter");
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, Name + ":getItem:position:" + position);
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        Log.d(TAG, Name + ":getCount");
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, Name + ":instantiateItem:container:" + container + ":position:" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d(TAG, Name + ":getItemPosition:object:" + object);
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = fragmentList.get(position).hashCode();
        Log.d(TAG, Name + ":getItemId:position:" + position);
        return hashCode;
//        return super.getItemId(position);
    }

    public void addFragment(String text) {
        SimpleTextFragment fragment = SimpleTextFragment.newInstance(text);
        fragmentList.add(0,fragment);
        notifyDataSetChanged();
    }



    public void removeFragment() {
        fragmentList.remove(0);
        notifyDataSetChanged();
    }

}
