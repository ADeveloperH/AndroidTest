package com.mobile.androidtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.circle_progress)
    MultiColorCircleProgress circleProgress;
    private List<Integer> arcPaintColorList = new ArrayList<>();
    private List<Float> arcPercentList = new ArrayList<>();
    private List<Float> remainPercentInSelfList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_two, R.id.btn_three})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_two:
                arcPaintColorList.clear();
                arcPercentList.clear();
                remainPercentInSelfList.clear();
                arcPaintColorList.add(Color.BLUE);
                arcPaintColorList.add(Color.RED);
                arcPercentList.add(0.7f);
                arcPercentList.add(0.3f);
                remainPercentInSelfList.add(0.5f);
                remainPercentInSelfList.add(0.6f);
                circleProgress.setMultiColorProgress(arcPaintColorList,arcPercentList,remainPercentInSelfList);
                break;
            case R.id.btn_three:
                arcPaintColorList.clear();
                arcPercentList.clear();
                remainPercentInSelfList.clear();
                arcPaintColorList.add(Color.BLUE);
                arcPaintColorList.add(Color.RED);
                arcPaintColorList.add(Color.GREEN);
                arcPercentList.add(0.2f);
                arcPercentList.add(0.3f);
                arcPercentList.add(0.5f);
                remainPercentInSelfList.add(0.5f);
                remainPercentInSelfList.add(0.5f);
                remainPercentInSelfList.add(0.5f);
                circleProgress.setMultiColorProgress(arcPaintColorList,arcPercentList,remainPercentInSelfList);
                break;
        }
    }
}
