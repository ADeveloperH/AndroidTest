package com.mobile.androidtest.factory;

import com.example.annotationlib.annotation.Factory;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description
 */
@Factory(type = Meal.class, id = "Tiramisu")
public class Tiramisu implements Meal {
    @Override
    public float getPrice() {
        return 4.5f;
    }
}
