package com.mobile.androidtest.factory;

import com.example.annotationlib.annotation.Factory;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description
 */
@Factory(type = Meal.class, id = "Margherita")
public class MargheritaPizza implements Meal {
    @Override
    public float getPrice() {
        return 6f;
    }
}
