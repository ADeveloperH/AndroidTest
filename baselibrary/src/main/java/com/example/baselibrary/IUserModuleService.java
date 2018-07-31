package com.example.baselibrary;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description 公共模块定义能被调用的服务
 */
public interface IUserModuleService extends IProvider {

    String getUserName(String userId);

}
