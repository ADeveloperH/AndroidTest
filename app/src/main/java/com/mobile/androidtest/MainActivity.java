package com.mobile.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

/**
 *
 * 参考链接：
 * https://wl9739.github.io/2017/01/13/Gson-中文指南/
 *
 * https://github.com/google/gson/blob/master/UserGuide.md#TOC-Null-Object-Support
 *
 */

public class MainActivity extends AppCompatActivity {

    String jsonStr = "{\"s\":null,\"i\":5}";
    String jsonStr2 = "{\n" +
            "    \"busiCode\": null,\n" +
            "    \"opId\": null,\n" +
            "    \"phone\": null,\n" +
            "    \"returnCode\": \"0000\",\n" +
            "    \"returnMessage\": \"接口请求成功！\",\n" +
            "    \"bean\": {},\n" +
            "    \"beans\": [],\n" +
            "    \"object\": {\n" +
            "        \"resultData\": {\n" +
            "            \"totalPoint\": \"1819\",\n" +
            "            \"brand\": \"01\"\n" +
            "        },\n" +
            "        \"transIdo\": \"14998532199131738824\",\n" +
            "        \"oprTime\": \"20170712175340\",\n" +
            "        \"resultCode\": \"0000\",\n" +
            "        \"resultDesc\": \"接口请求成功！\"\n" +
            "    },\n" +
            "    \"list\": null\n" +
            "}";

    String jsonStr3 = "{\"busiCode\":null,\"opId\":null,\"phone\":null,\"returnCode\":\"0000\",\"returnMessage\":\"接口请求成功！\",\"bean\":{},\"beans\":[],\"object\":{\"resultData\":{\"totalPoint\":\"1819\",\"brand\":\"01\"},\"transIdo\":\"14999116911221303823\",\"oprTime\":\"20170713100811\",\"resultCode\":\"0000\",\"resultDesc\":\"接口请求成功！\"},\"list\":null}";
    private String TAG = "huang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void parseJson(View view) {
        Bean bean = new Gson().fromJson(jsonStr, Bean.class);
        Log.d(TAG, "parseJson: " + bean.getS());
        Log.d(TAG, "parseJson: isEmpty:" + TextUtils.isEmpty(bean.getS()));
    }

    public void parseJson2(View view) {
        Bean3 bean = new Gson().fromJson(jsonStr3, Bean3.class);
        Log.d(TAG, "parseJson2: " + bean.getObject());
        Gson gson = new Gson();
        Log.d(TAG, "parseJson2: " + gson.toJson(bean.getObject()));
    }
}
