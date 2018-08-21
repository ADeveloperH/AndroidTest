package com.mobile.androidtest.flexboxlayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjian
 * @create 2018/8/21 0021
 * @Description
 */
public class FlexBoxActivity extends Activity {
    private FilterLablesLayout filterLablesLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int padding = dp2px(16);
        filterLablesLayout = new FilterLablesLayout(this);
        filterLablesLayout.setBackgroundColor(Color.WHITE);
        filterLablesLayout.setPadding(padding, padding, padding, padding);
        setContentView(filterLablesLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        List<String> filterList = new ArrayList<>();
        filterList.add("全部分类");
        filterList.add("红包");
        filterList.add("问答");
        filterList.add("邀请");

        filterLablesLayout.setLabelsData(filterList);
        filterLablesLayout.setOnItemClickListener(new FilterLablesLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(FlexBoxActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private int dp2px(int dip) {
        // dp和px的转换关系比例值
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
