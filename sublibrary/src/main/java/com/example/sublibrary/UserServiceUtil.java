package com.example.sublibrary;


import com.example.baselibrary.bean.UserBean;

/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description
 */
public class UserServiceUtil {
    public static UserBean getUserName(String userId) {
        return new UserBean("huangjian", 27);
    }
}
