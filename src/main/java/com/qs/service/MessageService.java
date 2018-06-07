package com.qs.service;

import com.qs.common.ResponseCode;

/**
 * 消息统一管理接口
 *
 * Created by fbin on 2018/6/7.
 */
public interface MessageService {

    /**
     * 发送消息处理
     * @param appId
     * @param paramData
     * @return
     */
    ResponseCode sendMessage(String appId, String paramData);

}
