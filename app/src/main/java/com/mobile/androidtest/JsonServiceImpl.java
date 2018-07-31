package com.mobile.androidtest;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;


/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description 如果需要传递自定义对象，需要实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)
 */
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return JSON.parseObject(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return JSON.toJSONString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return JSON.parseObject(input, clazz);
    }


    @Override
    public void init(Context context) {

    }
}
