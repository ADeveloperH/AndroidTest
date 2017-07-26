package com.mobile.androidtest;

/**
 * author：hj
 * time: 2017/7/26 0026 22:02
 */
public class TrafficBean {
    String pkgName;
    long monthRx;//当月接收字节
    long monthTx;//当月发送字节
    long dayRx;//当日接收字节
    long dayTx;//当日发送字节

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public long getMonthRx() {
        return monthRx;
    }

    public void setMonthRx(long monthRx) {
        this.monthRx = monthRx;
    }

    public long getMonthTx() {
        return monthTx;
    }

    public void setMonthTx(long monthTx) {
        this.monthTx = monthTx;
    }

    public long getDayRx() {
        return dayRx;
    }

    public void setDayRx(long dayRx) {
        this.dayRx = dayRx;
    }

    public long getDayTx() {
        return dayTx;
    }

    public void setDayTx(long dayTx) {
        this.dayTx = dayTx;
    }
}
