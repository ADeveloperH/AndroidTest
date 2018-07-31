package com.example.sublibrary;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.baselibrary.IUserModuleService;
import com.example.baselibrary.RouterPathUtil;

/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description 服务提供方实现对应接口
 */
@Route(path = RouterPathUtil.USER_MODULE_SERVICE, name = "测试服务")
public class SubUserModuleService implements IUserModuleService {
    @Override
    public String getUserName(String userId) {
        //具体调用的方法在这里定义
        return UserServiceUtil.getUserName(userId);
    }

    @Override
    public void init(Context context) {

    }
}
