package com.qs.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.qs.consumer.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 消息监听
 *
 * Created by fbin on 2018/6/7.
 */
public class MessageListener implements MessageListenerConcurrently {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private MessageHandler messageHandler;

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(
            List<MessageExt> list, ConsumeConcurrentlyContext context) {
        logger.info("开始消费消息...");
        for (MessageExt msg : list){
            boolean result = messageHandler.handleMessage(msg);
            if (!result){
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
