package com.mobile.androidtest;

import java.io.Serializable;

/**
 * @author huangjian
 * @create 2018/7/30 0030
 * @Description 注意，使用 Arouter 传递自定义对象，构造函数需要时空构造，否则获取为 null
 */
public class Test implements Serializable{
    private String fName;
    private String lName;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "Test{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }
}
