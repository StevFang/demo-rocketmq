package com.qs.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.qs.consumer.MessageHandler;
import com.qs.exception.RocketMQException;
import com.qs.listener.MessageListener;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * RocketMQ消费者配置中心
 *
 * Created by fbin on 2018/6/7.
 */
@SpringBootConfiguration
public class RocketMQConsumerConfiguration {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(RocketMQConsumerConfiguration.class);

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Value("${rocketmq.consumer.tag}")
    private String tag;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Resource(name = "messageHandler")
    private MessageHandler messageHandler; // 消息处理类

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMQException {
        if (StringUtils.isBlank(groupName)){
            throw new RocketMQException("groupName is null !!!");
        }
        if (StringUtils.isBlank(namesrvAddr)){
            throw new RocketMQException("namesrvAddr is null !!!");
        }
        if (StringUtils.isBlank(topic)){
            throw new RocketMQException("topic is null !!!");
        }
        if (StringUtils.isBlank(tag)){
            throw new RocketMQException("tag is null !!!");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        MessageListener messageListener = new MessageListener();
        messageListener.setMessageHandler(messageHandler);
        consumer.registerMessageListener(messageListener);
        try {
            consumer.subscribe(topic,this.tag);
            consumer.start();
            logger.info("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",
                    groupName,topic,namesrvAddr);
        }catch (MQClientException e){
            logger.error("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",
                    groupName,topic,namesrvAddr,e);
            throw new RocketMQException(e);
        }
        return consumer;
    }


}
