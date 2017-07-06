package com.mobile.androidtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：hj
 * time: 2017/7/2 0002 18:20
 */


public class BaseFragment extends Fragment {
    private String TAG = "hhh";
    public String FragmentName = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, FragmentName + ":onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, FragmentName + ":onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, FragmentName + ":onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, FragmentName + ":onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, FragmentName + ":onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, FragmentName + ":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, FragmentName + ":onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, FragmentName + ":onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, FragmentName + ":onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, FragmentName + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, FragmentName + ":onDetach");
    }
}
