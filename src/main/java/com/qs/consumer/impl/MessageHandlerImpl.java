package com.qs.consumer.impl;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.qs.consumer.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 订单消息处理类
 *
 * Created by fbin on 2018/6/7.
 */
@Component("messageHandler")
public class MessageHandlerImpl implements MessageHandler {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(MessageHandlerImpl.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        logger.info("receive : " + messageExt.toString());
        return true;
    }
}
