package com.qs.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * RocketMQ消费者
 *
 * Created by fbin on 2018/6/4.
 */
//@Component("rocketMQConsumer")
public class RocketMQConsumer {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);

    /**
     * 消费者的组名
     */
//    @Value("${rocketmq.consumer.consumerGroupName}")
    private String consumerGroup;

    /**
     * NameServer地址
     */
//    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

//    @PostConstruct
    public void defaultMQPushConsumer() {
        //消费者的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("DESKTOP-MNVUHTL", "push");
            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                    try {
                        for (MessageExt messageExt : list) {
                            logger.info("messageExt: " + messageExt);//输出消息内容
                            String messageBody = new String(messageExt.getBody(), "utf-8");
                            logger.info("消费响应：Msg: " + messageExt.getMsgId() + ",msgBody: " + messageBody);//输出消息内容
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("消费消息发生异常", e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
