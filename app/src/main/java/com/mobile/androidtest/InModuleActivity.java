package com.mobile.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselibrary.UserModuleServiceUtil;

// 为每一个参数声明一个字段，并使用 @Autowired 标注
// URL中不能传递Parcelable类型数据，通过ARouter api可以传递Parcelable对象
@Route(path = "/test/inmoduleactivity")
public class InModuleActivity extends AppCompatActivity {

    @Autowired
    public long key1;
    @Autowired
    public String key2;
    @Autowired(name = "key3")
    Test obj;

    private static final String TAG = "hj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_in_module);


        Intent intent = getIntent();
        if (intent != null) {
            long key1 = intent.getLongExtra("key1", -1l);
            String key2 = intent.getStringExtra("key2");

            Log.d(TAG, "onCreate: key1:" + key1);
            Log.d(TAG, "onCreate: key2:" + key2);
        }

        Log.d(TAG, "onCreate: key1:" + key1);
        Log.d(TAG, "onCreate: key2:" + key2);
        if (obj != null) {
            Log.d(TAG, "onCreate: obj:" + obj.toString());
        } else {
            Log.d(TAG, "onCreate: obj:" + obj);
        }


        findViewById(R.id.btn_invoke_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModuleServiceUtil.getUserName("123123123");
            }
        });
    }
}
