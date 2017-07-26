package com.mobile.androidtest;

import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Robert Zagórski on 2016-09-09.
 */
@TargetApi(Build.VERSION_CODES.M)
public class NetworkStatsHelper {

    NetworkStatsManager networkStatsManager;
    int packageUid;

    public NetworkStatsHelper(NetworkStatsManager networkStatsManager) {
        this.networkStatsManager = networkStatsManager;
    }

    public NetworkStatsHelper(NetworkStatsManager networkStatsManager, int packageUid) {
        this.networkStatsManager = networkStatsManager;
        this.packageUid = packageUid;
    }

    public long getAllRxBytesMobile(Context context) {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        return bucket.getRxBytes();
    }

    public long getAllTxBytesMobile(Context context) {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        return bucket.getTxBytes();
    }

    public long getAllRxBytesWifi() {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
                    "",
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        return bucket.getRxBytes();
    }

    public long getAllTxBytesWifi() {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
                    "",
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        return bucket.getTxBytes();
    }

    public long getPackageRxBytesMobile(Context context) {
        NetworkStats networkStats = null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    0,
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return -1;
        }
        int i = 0;
        long totalRx = 0L;
        while (networkStats.hasNextBucket()) {
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            Log.d("huang", "StartTime: " + bucket.getStartTimeStamp());
            Log.d("huang", "EndTime: " + bucket.getEndTimeStamp());
            networkStats.getNextBucket(bucket);
            totalRx += bucket.getRxBytes();
            Log.d("huang", "while::" + i + ":" + bucket.getRxBytes());
            i++;
        }
        return totalRx;
    }

    public long getPackageTxBytesMobile(Context context) {
        NetworkStats networkStats = null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    0,
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return -1;
        }
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }

    public long getPackageRxBytesWifi() {
        NetworkStats networkStats = null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI,
                    "",
                    0,
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return -1;
        }
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getRxBytes();
    }

    public long getPackageTxBytesWifi() {
        NetworkStats networkStats = null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI,
                    "",
                    0,
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return -1;
        }
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }



    /**
     * result[0]:Tx
     * result[1]:Rx
     * @param context
     * @return
     */
    public long[] getCurMonthRxTxBytesMobile(Context context) {
        long[] result = new long[2];
        NetworkStats networkStats = null;
        try {
            long beginMonth = DateUtils.getBeginDayOfMonth().getTime();
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    beginMonth,
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return result;
        }
        long totalRx = 0L;
        long totalTx = 0L;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        do{
            networkStats.getNextBucket(bucket);
            totalRx += bucket.getRxBytes();
            totalTx += bucket.getTxBytes();
        }while (networkStats.hasNextBucket());
        result[0] = totalTx;
        result[1] = totalRx;
        return result;
    }


    /**
     * result[0]:Tx
     * result[1]:Rx
     * @param context
     * @return
     */
    public long[] getCurDayRxTxBytesMobile(Context context) {
        long[] result = new long[2];
        NetworkStats networkStats = null;
        try {
            long current = System.currentTimeMillis();//当前时间毫秒数
            long zero = DateUtils.getDayBeginMill();
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    zero,
                    current,
                    packageUid);
        } catch (RemoteException e) {
            return result;
        }
        long totalRx = 0L;
        long totalTx = 0L;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        do{
            networkStats.getNextBucket(bucket);
            totalRx += bucket.getRxBytes();
            totalTx += bucket.getTxBytes();
        }while (networkStats.hasNextBucket());
        result[0] = totalTx;
        result[1] = totalRx;
        return result;
    }











    private String getSubscriberId(Context context, int networkType) {
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        }
        return "";
    }

    public void setPackageUid(int packageUid) {
        this.packageUid = packageUid;
    }
}
