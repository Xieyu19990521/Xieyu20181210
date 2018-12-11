package com.bawei.xieyu20181210.model;

public class LoginBean<T> {

    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"appkey":"dba243496835d7fe","appsecret":"075A1D91B6EA54A018F21DA8DA1CE0A8","createtime":"2018-12-10T08:57:56","email":null,"fans":null,"follow":null,"gender":null,"icon":null,"latitude":null,"longitude":null,"mobile":"15233802510","money":null,"nickname":null,"password":"8F669074CAF5513351A2DE5CC22AC04C","praiseNum":null,"token":"BB3D32DAA139E7646E92FD86DAE83DD5","uid":22989,"userId":null,"username":"15233802510"}
     */

    private String msg;
    private int code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
