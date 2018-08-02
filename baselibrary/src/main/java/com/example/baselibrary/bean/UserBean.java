package com.example.baselibrary.bean;

/**
 * @author huangjian
 * @create 2018/7/31 0031
 * @Description
 */
public class UserBean {
    private String userName;
    private int age;

    public UserBean(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
