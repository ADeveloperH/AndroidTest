package com.mobile.androidtest;

import java.util.List;

/**
 * author：hj
 * time: 2017/8/5 0005 00:53
 */


public class Bean3 {

    /**
     * busiCode : null
     * opId : null
     * phone : null
     * returnCode : 0000
     * returnMessage : 接口请求成功！
     * bean : {}
     * beans : []
     * object : null
     * list : null
     */

    private Object busiCode;
    private Object opId;
    private Object phone;
    private String returnCode;
    private String returnMessage;
    /**
     * object字段每个接口返回的不一样。可以先设置为Object类型
     * 后续可以使用toJson来将其转为对应的json串
     *
     */
    private Object object;
    private Object list;
    private List<?> beans;

    public Object getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(Object busiCode) {
        this.busiCode = busiCode;
    }

    public Object getOpId() {
        return opId;
    }

    public void setOpId(Object opId) {
        this.opId = opId;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public List<?> getBeans() {
        return beans;
    }

    public void setBeans(List<?> beans) {
        this.beans = beans;
    }
}
