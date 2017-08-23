package com.mobile.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    @BindView(R.id.btn_request_sync)
    Button btnRequestSync;
    @BindView(R.id.btn_request_async)
    Button btnRequestAsync;
    private OkHttpClient okHttpClient;
    private String requestUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13373931172";
    private Request request;
    private String TAG = "huang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);

        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(requestUrl)
                .build();
    }

    @OnClick({R.id.btn_request_sync, R.id.btn_request_async})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request_sync:
                Log.d(TAG, "onClick: btn_request_sync点击了");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String responseStr = okHttpClient.newCall(request).execute().body().string();
                            Log.d(TAG, "onClick: btn_request_sync:" + responseStr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "run: 同步请求发出了我可以干其他的事情了");
                    }
                }.start();
                break;
            case R.id.btn_request_async:
                Log.d(TAG, "onClick: btn_request_async点击了");
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onClick: btn_request_sync:" + response.body().string());
                    }
                });
                Log.d(TAG, "onClick: 异步请求已经发出了，我可以干其他的事情了");
                break;
        }
    }
}
