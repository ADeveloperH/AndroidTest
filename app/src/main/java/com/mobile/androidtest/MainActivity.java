package com.mobile.androidtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 流量统计：分为6.0之前和6.0之后
 * 6.0之前使用TrafficStats
 * 6.0之后使用NetworkStatsManager
 * 参考：
 * https://github.com/RobertZagorski/NetworkStats
 * https://developer.android.com/reference/android/net/TrafficStats.html#getUidRxBytes(int )
 * https://developer.android.com/reference/android/app/usage/NetworkStatsManager.html#queryDetailsForUid(int, java.lang.String, long, long, int)
 * https://developer.android.com/reference/android/app/usage/NetworkStats.html
 * https://developer.android.com/reference/android/app/usage/NetworkStats.Bucket.html
 */

public class MainActivity extends AppCompatActivity {
    private static final int READ_PHONE_STATE_REQUEST = 37;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    TrafficRVAdapter adapter;
    private Context context;
    private long startTime;
    private ArrayList<Boolean> stateList;
    private List<TrafficBean> dataList;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
    }


    private void getInfo(final NetworkStatsHelper networkStatsHelper) {
        startTime = System.currentTimeMillis();
        PackageManager pm = this.getPackageManager();
        //获取每个包内的androidmanifest.xml信息，它的权限等等
        final List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES
                | PackageManager.GET_PERMISSIONS);
        Log.d("huang", "总共包含" + packageInfos.size() + "个应用程序");
        //分多线程查询。加快速度
        if (packageInfos != null && packageInfos.size() > 0) {
            startTime = System.currentTimeMillis();
            dataList = new ArrayList<>();
//            final int count = 488;
//            stateList = new ArrayList<>();
            foreach(0, networkStatsHelper, packageInfos);
//            final int pieceCount = (int) Math.ceil(packageInfos.size() / (double) count);
//            for (int i = 0; i < pieceCount; i++) {
//                stateList.add(false);
//                final int finalI = i;
//                new Thread() {
//                    @Override
//                    public void run() {
//                        if (finalI == pieceCount - 1) {
//                            foreach(finalI, networkStatsHelper, packageInfos.subList(finalI * count, packageInfos.size()));
//                        } else {
//                            foreach(finalI, networkStatsHelper, packageInfos.subList(finalI * count, (finalI + 1) * count));
//                        }
//                    }
//                }.start();
//
//            }
        }
    }

    private void foreach(int index, NetworkStatsHelper networkStatsHelper, List<PackageInfo> packageInfos) {
        List<TrafficBean> resultList = new ArrayList<>();
        //遍历每个应用包信息
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            //请求每个程序包对应的androidManifest.xml里面的权限
            String[] premissions = packageInfo.requestedPermissions;
//            if (premissions != null && premissions.length > 0) {
//                //找出需要网络服务的应用程序
//                List<String> preList = Arrays.asList(premissions);
//                if (preList.contains("android.permission.INTERNET")) {
            int uId = packageInfo.applicationInfo.uid;//获取应用在操作系统内的进程id
            networkStatsHelper.setPackageUid(uId);
//                    long[] monthResult = networkStatsHelper.getCurMonthRxTxBytesMobile(this);
            long[] monthResult = {0, 0};
            long[] dayResult = networkStatsHelper.getCurDayRxTxBytesMobile(this);
            if (dayResult[0] > 0 || dayResult[1] > 0) {
                TrafficBean bean = new TrafficBean();
                bean.setPkgName(packageInfo.applicationInfo.packageName);
                bean.setMonthTx(monthResult[0]);
                bean.setMonthRx(monthResult[1]);
                bean.setDayTx(dayResult[0]);
                bean.setDayRx(dayResult[1]);
                resultList.add(bean);
            }
//                }
//            }
        }

        Log.d("huang", "查询完毕:i" + index + ":结果大小：" + resultList.size());
//        stateList.set(index, true);
        dataList = resultList;
        notifyShow();
    }

    private synchronized void notifyShow() {
//        for (int i = 0; i < stateList.size(); i++) {
//            if (!stateList.get(i)) {
//                return;
//            }
//        }
        Log.d("huang", "总耗时：" + ((System.currentTimeMillis() - startTime) / 1000) + "秒");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
                if (dataList != null && dataList.size() > 0) {
                    adapter = new TrafficRVAdapter(dataList);
                    recyclerview.setAdapter(adapter);
                } else {
                    Toast.makeText(context,"当前无应用消耗数据流量",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestPermissions();
    }

    private void requestPermissions() {
        if (!hasPermissionToReadPhoneStats()) {
            requestPhoneStateStats();
            return;
        }
        if (!hasPermissionToReadNetworkHistory()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) getApplicationContext()
                    .getSystemService(Context.NETWORK_STATS_SERVICE);
            final NetworkStatsHelper networkStatsHelper = new NetworkStatsHelper(networkStatsManager);
            loading = ProgressDialog.show(context, "", "正在加载中...", true, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    try {
                        if (null != context) {
                            loading.dismiss();
                        }
                    } catch (Exception e) {
                    }
                }
            });

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    getInfo(networkStatsHelper);
                }
            }.start();
        }


    }

    private boolean hasPermissionToReadPhoneStats() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            return true;
        }
    }

    private void requestPhoneStateStats() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST);
    }

    private boolean hasPermissionToReadNetworkHistory() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS,
                getApplicationContext().getPackageName(),
                new AppOpsManager.OnOpChangedListener() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onOpChanged(String op, String packageName) {
                        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                Process.myUid(), getPackageName());
                        if (mode != AppOpsManager.MODE_ALLOWED) {
                            return;
                        }
                        appOps.stopWatchingMode(this);
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        if (getIntent().getExtras() != null) {
                            intent.putExtras(getIntent().getExtras());
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                });
        requestReadNetworkHistoryAccess();
        return false;
    }

    private void requestReadNetworkHistoryAccess() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }


}
