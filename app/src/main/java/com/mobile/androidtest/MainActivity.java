package com.mobile.androidtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mobile.androidtest.adapter.MainFragmentAdapter;
import com.mobile.androidtest.fragment.SimpleTextFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    private MainFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (char i = 'A'; i < 'E'; i++) {
            SimpleTextFragment fragment = SimpleTextFragment.newInstance(String.valueOf(i));
            fragmentList.add(fragment);
        }
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager()
                , fragmentList);
        viewpager.setAdapter(fragmentAdapter);
    }

    @OnClick({R.id.btn_add, R.id.btn_update, R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                fragmentAdapter.addFragment("E");
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_delete:
                fragmentAdapter.removeFragment();
                break;
        }
    }
}
