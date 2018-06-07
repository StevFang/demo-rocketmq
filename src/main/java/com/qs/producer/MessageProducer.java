package com.qs.producer;

import com.alibaba.rocketmq.common.message.Message;
import com.qs.exception.RocketMQException;

import java.util.List;

/**
 * 消息发送接口类
 *
 * Created by fbin on 2018/6/4.
 */
public interface MessageProducer {

    /**
     * 发送单条消息
     * @param message
     */
    void sendMessage(Message message) throws RocketMQException;

    /**
     * 批量发送消息
     * @param messages
     */
    void sendMessages(List<Message> messages) throws RocketMQException;

}
