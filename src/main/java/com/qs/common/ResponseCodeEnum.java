package com.qs.common;

/**
 * code定义
 *
 * Created by fbin on 2018/6/7.
 */
public enum ResponseCodeEnum {

    SUCCESS("0", "响应成功"),
    ERROR("-1", "响应异常");


    private String code;
    private String msg;

    ResponseCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
