package com.mobile.androidtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * author：hj
 * time: 2018/3/13 0013 17:51
 * description:
 */


public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_DATA = 0;//展示数据
    public static final int VIEW_TYPE_EMPTY = 1;//展示无数据页面
    public static final int VIEW_TYPE_FOOTVIEW = 2;//底部view
    private final List<String> dataList;
    private final boolean isFootViewShowBtn;
    private final String moduleId = "NewAlreadyOrderActivity";

    public ItemRecyclerViewAdapter(boolean isFootViewShowBtn, List<String> dataList) {
        this.isFootViewShowBtn = isFootViewShowBtn;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_DATA) {
            View dataView = layoutInflater.inflate(R.layout.item_alreadyorder, parent, false);
            return new DataViewHodler(dataView);
        } else if (viewType == VIEW_TYPE_FOOTVIEW) {
            View footView = layoutInflater.inflate(R.layout.alreadyorder_footview, parent, false);
//            footView.setPadding(0,300,0,0);
            return new FootViewHodler(footView);
        } else {
            View emptyView = layoutInflater.inflate(R.layout.alreadyorder_empty, parent, false);
            return new RecyclerView.ViewHolder(emptyView) {
            };
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        if (dataList == null || dataList.size() == 0) {
            //显示空白页
            return 1;
        }
        //多显示一个底部view
        return dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList == null || dataList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            if (position == dataList.size()) {
                return VIEW_TYPE_FOOTVIEW;
            } else {
                return VIEW_TYPE_DATA;
            }
        }

    }

    public class DataViewHodler extends RecyclerView.ViewHolder {

        private final Context context;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvOrderTime;
        private TextView tvStartTime;
        private TextView tvTuiDing;
        private TextView tvBianGeng;

        public DataViewHodler(View itemView) {
            super(itemView);
            context = itemView.getContext();

            initView(itemView);
        }

        private void initView(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            tvStartTime = (TextView) itemView.findViewById(R.id.tv_invalid_time);
            tvTuiDing = (TextView) itemView.findViewById(R.id.tv_tuiding);
            tvBianGeng = (TextView) itemView.findViewById(R.id.tv_biangeng);
        }
    }

    public class FootViewHodler extends RecyclerView.ViewHolder {

        private final Context context;
        private LinearLayout llBtnContainer;
        private TextView tvGoTaoCan;
        private TextView tvGoBanLi;
        private TextView tvFootTip;
        private View fillView;

        public FootViewHodler(View itemView) {
            super(itemView);
            context = itemView.getContext();
            initView(itemView);
        }

        private void initView(View itemView) {
            llBtnContainer = (LinearLayout) itemView.findViewById(R.id.ll_btn_container);
            tvGoTaoCan = (TextView) itemView.findViewById(R.id.tv_goto_taocan);
            tvGoBanLi = (TextView) itemView.findViewById(R.id.tv_goto_banli);
            tvFootTip = (TextView) itemView.findViewById(R.id.tv_foot_tip);
            fillView = itemView.findViewById(R.id.view_fill);
            fillView.setVisibility(View.VISIBLE);
            llBtnContainer.setVisibility(View.GONE);
        }

    }
}
