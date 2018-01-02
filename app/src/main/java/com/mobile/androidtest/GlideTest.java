package com.mobile.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/12/31 0031 18:44
 * description:
 */


public class GlideTest extends Activity {
    @BindView(R.id.iv_wrap)
    ImageView ivWrap;
    @BindView(R.id.iv_test)
    ImageView ivTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_normal, R.id.btn_glide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                ivWrap.setImageResource(R.drawable.loaderror_1);
                ivTest.setImageResource(R.drawable.loaderror_1);
                break;
            case R.id.btn_glide:
                Glide.with(this)
                        .load(R.drawable.loaderror_1)
                        .asBitmap()
//                        .into(new SimpleTarget<GlideDrawable>() {
//                            @Override
//                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                                ivWrap.setImageDrawable(resource);
//                                int byteCount = ((GlideBitmapDrawable) resource).getBitmap().getByteCount();
//                                Log.d("huang", "onResourceReady: ivWrap：" + byteCount);
//                            }
//                        })
                .into(ivWrap)
                ;
                Glide.with(this)
                        .load(R.drawable.loaderror_1)
                        .asBitmap()
//                        .into(new SimpleTarget<GlideDrawable>() {
//                            @Override
//                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                                ivTest.setImageDrawable(resource);
//                                int byteCount = ((GlideBitmapDrawable) resource).getBitmap().getByteCount();
//                                Log.d("huang", "onResourceReady: ivTest：" + byteCount);
//
//                            }
//                        })
                .into(ivTest)

                ;
                break;
        }
//        BitmapDrawable drawableWrap = (BitmapDrawable) ivWrap.getBackground();
//        BitmapDrawable drawableTest = (BitmapDrawable) ivTest.getBackground();
//        Log.d("huang", "onClick: ivWrap:" + drawableWrap.getBitmap().getByteCount());
//        Log.d("huang", "onClick: ivTest:" + drawableTest.getBitmap().getByteCount());
    }
}
