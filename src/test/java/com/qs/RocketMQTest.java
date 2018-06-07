package com.qs;

import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.qs.producer.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by fbin on 2018/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RocketMQTest {

    @Resource(name = "orderMessageProducer")
    private MessageProducer orderMessageProducer;

    @Test
    public void testRocketMQ() throws Exception{
        Message message = new Message();
        message.setTopic("SELF_TEST_TOPIC");
        message.setTags("TEST");
        message.setKeys("kkk");
        message.setBody(new String("HelloRocketMQ!!!").getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 发送消息
        orderMessageProducer.sendMessage(message);
    }

}
