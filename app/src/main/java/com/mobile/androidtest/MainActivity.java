package com.mobile.androidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private String TAG = "hj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_in_module).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test test = new Test();
                test.setfName("Jack");
                test.setlName("Rouse");
                Log.d(TAG, "onClick: test:" + test.toString());
                ARouter.getInstance()
                        .build("/test/inmoduleactivity")
                        .withLong("key1", 555l)
                        .withString("key2", "888")
                        .withObject("key3", test)
                        .navigation(MainActivity.this, new NavCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                Log.d(TAG, "路由被目标发现");
                                super.onFound(postcard);
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.d(TAG, "路由被拦截");
                                super.onInterrupt(postcard);
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.d(TAG, "路由到达");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                Log.d(TAG, "路由丢失");
                                super.onLost(postcard);
                            }
                        });
            }
        });

    }
}
