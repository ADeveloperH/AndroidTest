package com.mobile.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(i + "");
        }

        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(false, dataList));

        recyclerView.addOnScrollListener(new RVItemVisibleScrollListener());
    }
}
