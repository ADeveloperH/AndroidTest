package com.example.baselibrary;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselibrary.bean.UserBean;

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
//        IUserModuleService iUserModuleService = (IUserModuleService) ARouter.getInstance()
//                .build(RouterPathUtil.USER_MODULE_SERVICE).navigation();

        //模块间通过类名调用服务
        IUserModuleService iUserModuleService = ARouter.getInstance().navigation(IUserModuleService.class);
        if (iUserModuleService != null) {
            UserBean userBean = iUserModuleService.getUserName(userId);
            Log.d(TAG, "onClick: 调用服务:userBean:" + userBean.toString());
        } else {
            Log.d(TAG, "onClick: 调用服务:iUserModuleService:" + iUserModuleService);
        }
        return "";
    }
}
