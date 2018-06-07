package com.qs.consumer;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 消费处理接口
 *
 * Created by fbin on 2018/6/4.
 */
public interface MessageHandler {

    /**
     * 处理消息的方法
     * @param messageExt
     * @return
     */
    boolean handleMessage(MessageExt messageExt);

}
