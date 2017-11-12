package com.mobile.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.androidtest.pagestate.PageListener;
import com.mobile.androidtest.pagestate.PageStateManager;

/**
 * author：hj
 * time: 2017/11/12 0012 17:07
 *
 *
 * 为什么使用newInstance
 * http://blog.csdn.net/incredible_wei/article/details/53023249
 */


public class BaseFragment extends Fragment {

    private static String ARG_PARAM = "content";
    private String content;
    private PageStateManager pageStateManager;

    public static BaseFragment newInstance(String title) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pageStateManager = new PageStateManager.Builder(getView().findViewById(R.id.ll_container)).build();
        TextView tvContent = (TextView) getView().findViewById(R.id.content);
        tvContent.setText(content);
        Button btnShowError = (Button) getView().findViewById(R.id.btn_show_error);

        btnShowError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageStateManager.showRetryView("这里模拟的是Fragment失败的页面");
            }
        });

        pageStateManager.setPageListener(new PageListener() {
            @Override
            public void onRetry(View retryClickView) {
                pageStateManager.showContent();
            }
        });

    }
}
