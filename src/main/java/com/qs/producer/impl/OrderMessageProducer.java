package com.qs.producer.impl;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.qs.exception.RocketMQException;
import com.qs.producer.MessageProducer;
import com.qs.producer.RocketMQProducer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbin on 2018/6/5.
 */
@Component("orderMessageProducer")
public class OrderMessageProducer implements MessageProducer {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;

    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize ; //4M

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private int sendMsgTimeout ;

    @Override
    public SendResult sendMessage(Message message) throws RocketMQException {
        DefaultMQProducer producer = initProducer();
        try{
            producer.start();

            SendResult result = producer.send(message);
            logger.info("消息:" + result.getMsgId() + ", 发送响应:" + result.getSendStatus());

            return result;
        }catch (Exception e){
            logger.error("消息发送发生异常", e);
            throw new RocketMQException(e);
        }finally {
            producer.shutdown();
        }
    }

    @Override
    public List<SendResult> sendMessages(List<Message> messages) throws RocketMQException {
        DefaultMQProducer producer = initProducer();

        try{
            producer.start();

            List<SendResult> sendResults = new ArrayList<>();
            for(Message message : messages){
                SendResult result = producer.send(message);
                logger.info("消息:" + result.getMsgId() + ", 发送响应:" + result.getSendStatus());
                sendResults.add(result);
            }

            return sendResults;
        }catch (Exception e){
            logger.error("消息发送发生异常", e);
            throw new RocketMQException(e);
        }finally {
            producer.shutdown();
        }
    }

    /**
     * 初始化 Producer 配置
     */
    private DefaultMQProducer initProducer() throws RocketMQException{
        if (StringUtils.isBlank(this.groupName)) {
            throw new RocketMQException("groupName is blank");
        }
        if (StringUtils.isBlank(this.namesrvAddr)) {
            throw new RocketMQException("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(this.instanceName)){
            throw new RocketMQException("instanceName is blank");
        }

        // 生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        // 指定NameServer地址,多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);

        return producer;
    }
}
