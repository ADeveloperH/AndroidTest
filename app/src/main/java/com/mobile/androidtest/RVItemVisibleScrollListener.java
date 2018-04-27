package com.mobile.androidtest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/**
 * author：hj
 * time: 2018/4/27 0027 14:30
 * description:
 */


public class RVItemVisibleScrollListener extends RecyclerView.OnScrollListener {

    private int preFirstPosition = -1;
    private int preLastPosition = -1;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int childCount = recyclerView.getChildCount();
//        Log.d("huang", "onScrolled: childCount:" + childCount);

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
//        Log.d("huang", "onScrolled: itemCount:" + itemCount);
//        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
//        Log.d("huang", "onScrolled: firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition);
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//        Log.d("huang", "onScrolled: firstVisibleItemPosition:" + firstVisibleItemPosition);
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//        Log.d("huang", "onScrolled: lastVisibleItemPosition:" + lastVisibleItemPosition);
//        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
//        Log.d("huang", "onScrolled: lastCompletelyVisibleItemPosition:" + lastCompletelyVisibleItemPosition);

        if (preFirstPosition == -1 || preLastPosition == -1 ||
                firstVisibleItemPosition > preLastPosition || lastVisibleItemPosition < preFirstPosition) {
            //两次区间为相交
            Log.d("huang", "onScrolled: 差异区间为：" + firstVisibleItemPosition + "~" + lastVisibleItemPosition);
            getNewViewData(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition);
        } else {
            //两次区间相交
            if (firstVisibleItemPosition >= preFirstPosition
                    && lastVisibleItemPosition > preLastPosition) {
                //向上滑动了
                Log.d("huang", "onScrolled: 向上滑动了新增曝光区间为：" + (preLastPosition + 1) + "~" + lastVisibleItemPosition);
                getNewViewData(recyclerView, preLastPosition + 1, lastVisibleItemPosition);
            } else if (firstVisibleItemPosition < preFirstPosition
                    && lastVisibleItemPosition <= preLastPosition) {
                //向下滑动了
                Log.d("huang", "onScrolled: 向下滑动了新增曝光区间为：" + firstVisibleItemPosition + "~" + (preFirstPosition - 1));
                getNewViewData(recyclerView, firstVisibleItemPosition, preFirstPosition - 1);
            }
        }


        preFirstPosition = firstVisibleItemPosition;
        preLastPosition = lastVisibleItemPosition;
    }

    private void getNewViewData(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition) {
        ItemRecyclerViewAdapter adapter = (ItemRecyclerViewAdapter) recyclerView.getAdapter();
        List<String> dataList = adapter.getDataList();
        if (firstVisibleItemPosition >= lastVisibleItemPosition &&
                firstVisibleItemPosition >= 0 && lastVisibleItemPosition < dataList.size()) {
            Log.d("huang", "getNewViewData: ==================================================");
            for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                Log.d("huang", "getNewViewData: " + dataList.get(i));
            }
        }


    }

}
