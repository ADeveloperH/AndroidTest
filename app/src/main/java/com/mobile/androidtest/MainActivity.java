package com.mobile.androidtest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 悬浮窗相关：
 * https://www.liaohuqiu.net/cn/posts/android-windows-manager/
 * 源码分析：http://www.jianshu.com/p/634cd056b90c
 * 仿UC复制：https://github.com/liaohuqiu/android-UCToast
 * <p>
 * 解决权限封锁：
 * http://blog.csdn.net/qq_25867141/article/details/52807705
 * 代码实现：https://github.com/Blincheng/EToast2
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edittext)
    EditText edittext;
    private Context context;
    private int text;
    private String TAG = "huang";
    private WindowManager wManger;
    private View contentView;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
    }


    public void showWM(View view) {
        if (wManger != null && contentView != null) {
            wManger.removeView(contentView);
            wManger.removeViewImmediate(contentView);
        }
        String editStr = edittext.getText().toString().trim();
        if (TextUtils.isEmpty(editStr)) {
            return;
        }
        if (TextUtils.isEmpty(filterUnNumber(editStr))) {
            return;
        }

        char[] numberArray = filterUnNumber(editStr).toCharArray();
        contentView = View.inflate(context, R.layout.wm_content_view, null);
        LinearLayout llContainer = (LinearLayout) contentView.findViewById(R.id.ll_num_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (llContainer.getChildCount() > 2) {
            for (int i = 2; i < llContainer.getChildCount(); i++) {
                llContainer.removeView(llContainer.getChildAt(i));
            }
        }

        for (int i = 0, length = numberArray.length; i < length; i++) {
            ImageView imageView = new ImageView(context);
            switch (numberArray[i]) {
                case '0':
                    imageView.setImageResource(R.drawable.heweibi_0);
                    break;
                case '1':
                    imageView.setImageResource(R.drawable.heweibi_1);
                    break;
                case '2':
                    imageView.setImageResource(R.drawable.heweibi_2);
                    break;
                case '3':
                    imageView.setImageResource(R.drawable.heweibi_3);
                    break;
                case '4':
                    imageView.setImageResource(R.drawable.heweibi_4);
                    break;
                case '5':
                    imageView.setImageResource(R.drawable.heweibi_5);
                    break;
                case '6':
                    imageView.setImageResource(R.drawable.heweibi_6);
                    break;
                case '7':
                    imageView.setImageResource(R.drawable.heweibi_7);
                    break;
                case '8':
                    imageView.setImageResource(R.drawable.heweibi_8);
                    break;
                case '9':
                    imageView.setImageResource(R.drawable.heweibi_9);
                    break;
            }
            layoutParams.setMargins(8, 0, 0, 0);
            llContainer.addView(imageView, layoutParams);
        }
        if (wManger == null) {
            wManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        if (params == null) {
            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.gravity = Gravity.CENTER;
        }
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissWM();
            }
        });
        wManger.addView(contentView, params);

    }

    private void dismissWM() {
        if (wManger != null && contentView != null) {
            wManger.removeViewImmediate(contentView);
            contentView = null;
        }
    }

    public static String filterUnNumber(String str) {
        // 只允数字
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        return m.replaceAll("").trim();

    }


    /**
     * DecorView参考链接
     * https://juejin.im/entry/59116dd044d904007bf99e09
     *
     * @param view
     */
    public void showDV(View view) {
        final ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        final View addView = View.inflate(context, R.layout.wm_content_view, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        rootView.addView(addView, params);

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rootView != null && addView != null) {
                    rootView.removeView(addView);
                }
            }
        });
    }


    /**
     * 显示对话框
     * @param view
     */
    public void showDialog(View view) {
        new AlertDialog.Builder(context)
                .setTitle("测试对话框")
                .setMessage("对话框显示的文本信息")
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null).create().show();
    }
}
