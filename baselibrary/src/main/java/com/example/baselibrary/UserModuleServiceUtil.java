package com.example.baselibrary;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author huangjian
 * @create 2018/7/31 0031
 * @Description
 */
public class UserModuleServiceUtil {
    private static final String TAG = "hj";

    public static String getUserName(String userId) {
        //服务的发现。调用服务的方法

        //模块间通过路径名称调用服务
                IUserModuleService iUserModuleService = (IUserModuleService) ARouter.getInstance()
                        .build(RouterPathUtil.USER_MODULE_SERVICE).navigation();
//                String userName = iUserModuleService.getUserName("2324");
//                Log.d(TAG, "onClick: 通过路径名称调用服务:userName:" + userName);


        //模块间通过类名调用服务
//        IUserModuleService iUserModuleService = ARouter.getInstance().navigation(IUserModuleService.class);
        if (iUserModuleService != null) {
            String userName = iUserModuleService.getUserName(userId);
            Log.d(TAG, "onClick: 通过类名调用服务:userName:" + userName);
        } else {
            Log.d(TAG, "onClick: 通过类名调用服务:iUserModuleService:" + iUserModuleService);
        }
        return "";
    }
}
