package com.mobile.androidtest;

import java.util.List;

/**
 * author：hj
 * time: 2017/8/5 0005 00:30
 */


public class Bean2 {

    /**
     * busiCode : null
     * opId : null
     * phone : null
     * returnCode : 0000
     * returnMessage : 接口请求成功！
     * bean : {}
     * beans : []
     * object : {"resultData":{"totalPoint":"1819","brand":"01"},"transIdo":"14998532199131738824","oprTime":"20170712175340","resultCode":"0000","resultDesc":"接口请求成功！"}
     * list : null
     */

    private Object busiCode;
    private Object opId;
    private Object phone;
    private String returnCode;
    private String returnMessage;
    /**
     * resultData : {"totalPoint":"1819","brand":"01"}
     * transIdo : 14998532199131738824
     * oprTime : 20170712175340
     * resultCode : 0000
     * resultDesc : 接口请求成功！
     */

    private ObjectEntity object;
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

    public ObjectEntity getObject() {
        return object;
    }

    public void setObject(ObjectEntity object) {
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

    public static class ObjectEntity {
        /**
         * totalPoint : 1819
         * brand : 01
         */

        private ResultDataEntity resultData;
        private String transIdo;
        private String oprTime;
        private String resultCode;
        private String resultDesc;

        public ResultDataEntity getResultData() {
            return resultData;
        }

        public void setResultData(ResultDataEntity resultData) {
            this.resultData = resultData;
        }

        public String getTransIdo() {
            return transIdo;
        }

        public void setTransIdo(String transIdo) {
            this.transIdo = transIdo;
        }

        public String getOprTime() {
            return oprTime;
        }

        public void setOprTime(String oprTime) {
            this.oprTime = oprTime;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultDesc() {
            return resultDesc;
        }

        public void setResultDesc(String resultDesc) {
            this.resultDesc = resultDesc;
        }

        public static class ResultDataEntity {
            private String totalPoint;
            private int brand;

            public String getTotalPoint() {
                return totalPoint;
            }

            public void setTotalPoint(String totalPoint) {
                this.totalPoint = totalPoint;
            }

            public int getBrand() {
                return brand;
            }

            public void setBrand(int brand) {
                this.brand = brand;
            }
        }
    }
}
