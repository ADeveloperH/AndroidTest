package com.mobile.androidtest.factory;

import com.example.annotationlib.annotation.Factory;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description
 */
@Factory(type = Meal.class, id = "Calzone")
public class CalzonePizze implements Meal {
    @Override
    public float getPrice() {
        return 8.5f;
    }
}
