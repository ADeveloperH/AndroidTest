package com.mobile.androidtest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author：hj
 * time: 2017/7/22 0022 23:09
 */


public class TrafficStasUtils {

    /**
     * 获取当前使用流量的应用信息
     *
     * @param context
     * @return
     */
    public static List<Map<String, Object>> getCurrentTrafficInfos(Context context) {
        List<Map<String, Object>> list_trafficinfos = new ArrayList<Map<String, Object>>();
        PackageManager pm = context.getPackageManager();
        //获取每个包内的androidmanifest.xml信息，它的权限等等
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES
                | PackageManager.GET_PERMISSIONS);
        //遍历每个应用包信息
        for (PackageInfo packageInfo : packageInfos) {
            //请求每个程序包对应的androidManifest.xml里面的权限
            String[] premissions = packageInfo.requestedPermissions;
            if (premissions != null && premissions.length > 0) {
                //找出需要网络服务的应用程序
                List<String> preList = Arrays.asList(premissions);
                if (preList.contains("android.permission.INTERNET")) {
                    int uId = packageInfo.applicationInfo.uid;//获取应用在操作系统内的进程id
                    long rx = TrafficStats.getUidRxBytes(uId);//某一个进程的总接收量.如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
                    long tx = TrafficStats.getUidTxBytes(uId);//某一个进程的总发送量

                    Log.d("huang", packageInfo.applicationInfo.packageName + "rx_tx:" + rx + tx);
                    if (rx < 0 || tx < 0) {
                        continue;
                    } else {
                        if (rx > 0 || tx > 0) {
                            Log.d("huang", packageInfo.applicationInfo.packageName + "rx_tx:" + rx + tx);
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("pkg", packageInfo.applicationInfo.packageName);
                        map.put("rx_tx", rx + tx);
                        map.put("rx", rx);
                        map.put("tx", tx);
                        list_trafficinfos.add(map);
                    }
                }
            }
        }
        return list_trafficinfos;
    }
}
