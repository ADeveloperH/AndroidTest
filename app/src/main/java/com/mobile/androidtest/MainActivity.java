package com.mobile.androidtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public static final String EXTRA_PACKAGE = "ExtraPackage";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);


//        TrafficStasUtils.getCurrentTrafficInfos(this);

    }

    private String getInfo(NetworkStatsHelper networkStatsHelper) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Map<String, Object>> list_trafficinfos = new ArrayList<Map<String, Object>>();
        PackageManager pm = this.getPackageManager();
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
//                if (preList.contains("android.permission.INTERNET")) {
//                    Log.d("huang", packageInfo.applicationInfo.packageName);
                if (packageInfo.applicationInfo.packageName.contains("zz") ||
                        packageInfo.applicationInfo.packageName.contains("com.qihoo.appstore")) {
                    int uId = packageInfo.applicationInfo.uid;//获取应用在操作系统内的进程id
                    networkStatsHelper.setPackageUid(uId);

                    int rx = (int) (networkStatsHelper.getPackageRxBytesMobile(this) / 1024);

                    Log.d("huang", packageInfo.applicationInfo.packageName + ":rx:" + rx + "KB");

                    int monthRx = (int) (networkStatsHelper.getCurMonthRxBytesMobile(this) / 1024);
                    int tx = (int) (networkStatsHelper.getPackageTxBytesMobile(this) / 1024);

                    Log.d("huang", packageInfo.applicationInfo.packageName + ":monthRx:" + monthRx + "KB");
                    int dayRx = (int) (networkStatsHelper.getCurDayPackageRxBytesMobile(this) / 1024);
                    Log.d("huang", packageInfo.applicationInfo.packageName + ":dayRx:" + dayRx + "KB");
                    stringBuilder.append(packageInfo.applicationInfo.packageName + ":rx:" + rx + "KB")
                            .append("\n")
                            .append(packageInfo.applicationInfo.packageName + ":monthRx:" + monthRx + "KB")
                            .append("\n")
                            .append(packageInfo.applicationInfo.packageName + ":dayRx:" + dayRx + "KB")
                            .append("\n");
                }
            }
        }
        Log.d("huang", "resutl:" + stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    private void requestPermissions() {
        if (!hasPermissionToReadNetworkHistory()) {
            return;
        }
        if (!hasPermissionToReadPhoneStats()) {
            requestPhoneStateStats();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) getApplicationContext()
                    .getSystemService(Context.NETWORK_STATS_SERVICE);
            final NetworkStatsHelper networkStatsHelper = new NetworkStatsHelper(networkStatsManager);

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    final String result = getInfo(networkStatsHelper);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });

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
                android.os.Process.myUid(), getPackageName());
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
                                android.os.Process.myUid(), getPackageName());
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
