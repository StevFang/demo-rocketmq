package com.qs.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * RocketMQ生产者
 *
 * Created by fbin on 2018/6/4.
 */
//@Component("rocketMQProducer")
public class RocketMQProducer{

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

    /**
     * 生产者的组名
     */
//    @Value("${rocketmq.producer.producerGroupName}")
    private String producerGroup;

    /**
     * NameServer 地址
     */
//    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

//    @PostConstruct
    public void defaultMQProducer() {
        // 生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        // 指定NameServer地址,多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();
            for (int i = 0; i < 100; i++) {
                // 消息体
                String messageBody = "我是消息内容:" + i;
                // 消息体 转换成 utf-8 字节码
                String message = new String(messageBody.getBytes(), "utf-8");
                /**
                 * 构建消息
                 * topic: 主题
                 * tags: 标签
                 * keys: 键
                 * body: 消息内容
                 */
                Message msg = new Message("DESKTOP-MNVUHTL", "push", "key_" + i, message.getBytes());
                //发送消息
                SendResult result = producer.send(msg);
                logger.info("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }

    }
}
