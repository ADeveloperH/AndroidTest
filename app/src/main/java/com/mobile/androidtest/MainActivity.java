package com.mobile.androidtest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageview)
    ImageView imageview;
    private String TAG = "huang";
    private Context context;

    private String imageUrl = "http://img5q.duitang.com/uploads/item/201501/11/20150111083321_ijNej.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        final Dialog dialog = new Dialog(this);
        ImageView mImageView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(500,500);
        dialog.setContentView(mImageView,params);
        Glide.with(this)
                .load("http://img5q.duitang.com/uploads/item/201501/11/20150111083321_ijNej.jpeg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onException: " + e.getLocalizedMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: ");
//                        imageview.setVisibility(View.VISIBLE);
                        dialog.show();
                        return false;
                    }
                })
                .into(mImageView);
//        new Thread() {
//
//            @Override
//            public void run() {
//                super.run();
//                //先缓存
//                Glide.with(context)
//                        .load(imageUrl)
//                        .downloadOnly(500, 500);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.with(context)
//                                .load(imageUrl)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(imageview);
//                    }
//                });
//            }
//        }.start();


    }
}
