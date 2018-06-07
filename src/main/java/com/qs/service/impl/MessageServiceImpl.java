package com.qs.service.impl;

import com.qs.common.ResponseCode;
import com.qs.common.ResponseCodeEnum;
import com.qs.producer.MessageProducer;
import com.qs.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息统一管理业务类
 *
 * Created by fbin on 2018/6/7.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource(name = "orderMessageProducer")
    private MessageProducer orderMessageProducer;

    @Override
    public ResponseCode sendMessage(String appId, String paramData) {
        // 获取密钥

        // 数据解密

        // 数据

        return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, "");
    }
}
