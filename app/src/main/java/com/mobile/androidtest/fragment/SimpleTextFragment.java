package com.mobile.androidtest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.androidtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/7/2 0002 18:43
 */


public class SimpleTextFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView text;

    public static SimpleTextFragment newInstance(String text) {
        SimpleTextFragment fragment = new SimpleTextFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_simple_txt, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            text.setText(bundle.getString("text"));
        }
    }
}
