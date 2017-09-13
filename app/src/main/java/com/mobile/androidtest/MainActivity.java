package com.mobile.androidtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 * 参考：
 *      http://www.jianshu.com/p/e06132ce2a97
 * 总结：
 * 自定义的URI Sheme和Intent-based URI在微信中都无法打开APP进行传参，支持QQ和其他浏览器中打开APP传参。
 * 应用宝微下载支持APP Link，需要申请，但不支持传参，只支持打开APP具体页面。
 *
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_disklrucache)
    Button btnDisklrucache;
    @BindView(R.id.btn_okhttp)
    Button btnOkhttp;
    private String TAG = "hj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        /**
         * Intent方式生成链接的代码
         */
//        Intent testintent = new Intent();
//        testintent.setAction(Intent.ACTION_VIEW);
//        testintent.addCategory(Intent.CATEGORY_DEFAULT);
//        testintent.addCategory(Intent.CATEGORY_BROWSABLE);
//        testintent.setData(Uri.parse("hj://testscheme?uid=一只普普通通的android开发同学"));
//        testintent.putExtra("me", "Yunr say hello!");
//        Log.d(TAG, "onCreate: intent0:" + testintent.toUri(0));



        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                Log.d(TAG, "onCreate: intent0:" + intent.toUri(0) + "\n" + intent.getDataString()+"\n"+uri.getQueryParameter("uid"));

                String params = uri.getQueryParameter("params");
                Toast.makeText(this, "获取到值了：" + params,Toast.LENGTH_LONG).show();
                Log.d(TAG, "onCreate: params:" + params);
            } else {
                Log.d(TAG, "onCreate: uri == null");
            }
        } else {
            Log.d(TAG, "onCreate: intent == null");
        }
    }

    @OnClick({R.id.btn_disklrucache, R.id.btn_okhttp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_disklrucache:
                startActivity(new Intent(this,DiskLruCacheActivity.class));
                break;
            case R.id.btn_okhttp:
                startActivity(new Intent(this,OkHttpActivity.class));
                break;
        }
    }
}
