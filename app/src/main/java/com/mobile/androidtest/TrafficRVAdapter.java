package com.mobile.androidtest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/7/26 0026 22:04
 */


public class TrafficRVAdapter extends RecyclerView.Adapter<TrafficRVAdapter.MyViewHolder> {


    private List<TrafficBean> trafficBeanList;

    public TrafficRVAdapter(List<TrafficBean> trafficBeanList) {
        this.trafficBeanList = trafficBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TrafficBean bean = trafficBeanList.get(position);
        long monthRx = bean.getMonthRx();
        long monthTx = bean.getMonthTx();
        long monthRx_Tx = monthRx + monthTx;
        long dayRx = bean.getDayRx();
        long dayTx = bean.getDayTx();
        long dayRx_Tx = dayRx + dayTx;

        holder.tvMonthTxRx.setText("当月总量：" + Html.fromHtml(formatTraffic(monthRx_Tx)));
        holder.tvMonthTx.setText("当月发送量：" + Html.fromHtml(formatTraffic(monthTx)));
        holder.tvMonthRx.setText("当月接收量：" + Html.fromHtml(formatTraffic(monthRx)));

        holder.tvDayTxRx.setText("当日总量：" + Html.fromHtml(formatTraffic(dayRx_Tx)));
        holder.tvDayTx.setText("当日发送量：" + Html.fromHtml(formatTraffic(dayTx)));
        holder.tvDayRx.setText("当日接收量：" + Html.fromHtml(formatTraffic(dayRx)));

        try {
            holder.ivPkg.setImageDrawable(holder.context.getPackageManager()
                    .getApplicationIcon(bean.getPkgName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return trafficBeanList == null ? 0 : trafficBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pkg)
        ImageView ivPkg;
        @BindView(R.id.tv_month_tx_rx)
        TextView tvMonthTxRx;
        @BindView(R.id.tv_month_tx)
        TextView tvMonthTx;
        @BindView(R.id.tv_month_rx)
        TextView tvMonthRx;
        @BindView(R.id.tv_day_tx_rx)
        TextView tvDayTxRx;
        @BindView(R.id.tv_day_tx)
        TextView tvDayTx;
        @BindView(R.id.tv_day_rx)
        TextView tvDayRx;
        public Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }
    }

    private String formatTraffic(long traffic) {
        String prefix = "<small><small>";
        String suffix = "B</small></small>";
        String trafficTmp = null;
        long tmp = traffic;
        if (tmp < 1024) {
            trafficTmp = String.format("%.2f", (float) tmp);
            ;
            suffix = "B</small></small>";
        } else if (tmp >= 1024 && tmp < 1024 * 1024) {
            trafficTmp = String.format("%.2f", (float) tmp / (float) 1024);
            suffix = "KB</small></small>";
        } else if (tmp >= 1024 * 1024 && tmp < 1024 * 1024 * 1024) {
            trafficTmp = String.format("%.2f", (float) tmp / (float) 1024 / (float) 1024);
            suffix = "MB</small></small>";
        } else {
            trafficTmp = String.format("%.2f", (float) tmp / (float) 1024 / (float) 1024 / (float) 1024);
            suffix = "GB</small></small>";
        }

        return formatData(trafficTmp, prefix, suffix);
    }

    private String formatData(String data, String prefix, String suffix) {
        if (data != null && !"".equals(data)) {
            return data.contains(".") ? data.substring(0, data.indexOf("."))
                    + prefix + data.substring(data.indexOf("."), data.length())
                    + suffix : data + prefix + suffix;
        }

        return "0" + prefix + suffix;
    }

    public void addListAndNotify(List<TrafficBean> trafficBeanList) {
        int oldSize = this.trafficBeanList.size();
        this.trafficBeanList.addAll(trafficBeanList);
        notifyItemInserted(oldSize);
    }

}
