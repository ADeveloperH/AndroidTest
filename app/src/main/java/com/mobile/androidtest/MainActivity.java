package com.mobile.androidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvHelloJNI;
    private TextView tvSignature;
    private TextView tvCurSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initView() {
        tvHelloJNI = (TextView) findViewById(R.id.tv_hellojni);
        tvCurSignature = (TextView) findViewById(R.id.tv_cursignature);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
    }

    private void initData() {
        String nativeHelloStr = JNIUtil.getNativeStr();
        Toast.makeText(this, nativeHelloStr, Toast.LENGTH_LONG).show();
        tvHelloJNI.setText(nativeHelloStr);


        String signature = JNIUtil.getSignature(this);

        Log.d("hj", "initData: signature:" + signature);
        tvCurSignature.setText(signature);

        tvSignature.setText(SignatureUtil.checkSignature(this));
    }
}
