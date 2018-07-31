package com.mobile.androidtest;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查,
 * 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
 */
@Interceptor(priority = 6, name = "测试拦截6")
public class TestInterceptor implements IInterceptor {
    private static final String TAG = "hj";

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(TAG, "process: TestInterceptor 继续");
        callback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程

        // 以上两种至少需要调用其中一种，否则不会继续路由
    }

    @Override
    public void init(Context context) {
        Log.d(TAG, "init: TestInterceptor");
    }
}
