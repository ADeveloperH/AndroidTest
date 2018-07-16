package com.mobile.androidtest;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AppCompatTextView tvScale;
    private EditText editText;
    private TextView tvCurSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }


    /**
     * 注意：
     * 1、需要使用support中的TextView：android.support.v7.widget.AppCompatTextView
     * 2、AppCompatTextView 的width 和 height 必须为具体的值，不能为 wrap_content
     * 3、设置单行显示使用：android:maxLines="1"，不要使用 android:singleLine="true"
     *
     */
    private void initView() {
        editText = findViewById(R.id.et);
        tvScale = findViewById(R.id.tv_scale);
        tvCurSize = findViewById(R.id.tv_cur_size);
        initScaleConfig();
        tvScale.setText("Hello ");
        tvCurSize.setText("当前的字体大小：" + tvScale.getTextSize());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String etStr = s.toString().trim();
                if (!TextUtils.isEmpty(etStr)) {
                    tvScale.setText(etStr);

                    tvCurSize.setText("当前的字体大小：" + tvScale.getTextSize());
                }
            }
        });
    }


    /**
     * 设置自动调整的字体尺寸大小
     * 有三种方式来设置字体大小尺寸信息：
     * 1、默认(Default)
     * 2、粒度(Granularity)
     * 3、预设尺寸(Preset Sizes)
     */
    private void initScaleConfig() {
        /**
         * android:autoSizeTextType="uniform"
         * the type of auto-size. Must be one of
         * {@link TextViewCompat#AUTO_SIZE_TEXT_TYPE_NONE} or
         * {@link TextViewCompat#AUTO_SIZE_TEXT_TYPE_UNIFORM}
         * none: 关闭缩放功能
         * uniform: 垂直方向与水平方向缩放
         */
//        TextViewCompat.setAutoSizeTextTypeWithDefaults(tvScale, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);


        /**
         * 参数一： 被设置的TextView
         * 参数二： 自动缩放的最小字号
         * 参数三： 自动缩放的最大字号
         * 参数四： he auto-size step granularity. It is used in conjunction with
         *                                the minimum and maximum text size in order to build the set of
         *                                text sizes the system uses to choose from when auto-sizing
         * 参数五： 参数二与参数三所用的单位
         */
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvScale, 6,
                60, 2, TypedValue.COMPLEX_UNIT_DIP);

        /**
         * 参数一：
         * 参数二： 设置多个预制字体大小，这样在缩放时字体会根据预制的字体大小而缩放
         * 参数三： 参数二里面的Int值对应的单位
         */
//        int[] autoTextSize = getResources().getIntArray(R.array.autosize_text_sizes);
//        TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(tv_scale, autoTextSize, TypedValue.COMPLEX_UNIT_SP);

    }
}
