package com.qs.common;

import lombok.Data;

/**
 * 返回消息
 *
 * Created by fbin on 2018/6/7.
 */
@Data
public class ResponseCode {

    private String code;

    private String msg;

    private Object data;

    private ResponseCode(){

    }

    /**
     * 获取实例
     * @param responseCodeEnum
     * @param data
     * @return
     */
    public static ResponseCode getInstance(ResponseCodeEnum responseCodeEnum, Object data){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(responseCodeEnum.getCode());
        responseCode.setMsg(responseCodeEnum.getMsg());
        responseCode.setData(data);
        return responseCode;
    }
}
