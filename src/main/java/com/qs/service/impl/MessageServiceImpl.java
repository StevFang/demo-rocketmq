package com.qs.service.impl;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.qs.CacheInit;
import com.qs.common.MessageInfo;
import com.qs.common.ResponseCode;
import com.qs.common.ResponseCodeEnum;
import com.qs.producer.MessageProducer;
import com.qs.service.MessageService;
import com.qs.utils.AESCoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息统一管理业务类
 *
 * Created by fbin on 2018/6/7.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource(name = "orderMessageProducer")
    private MessageProducer orderMessageProducer;

    @Override
    public ResponseCode sendMessage(String appId, String paramData) {
        Map<String, Object> data = new HashMap<>();
        // appId不能为空
        if(StringUtils.isBlank(appId)){
            data.put("code", "1");
            data.put("msg", "appId为空");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
        // paramData不能为空
        if(StringUtils.isBlank(paramData)){
            data.put("code", "3");
            data.put("msg", "参数paramData为空");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
        // 获取密钥
        String key = CacheInit.getKey(appId);
        if(StringUtils.isBlank(key)){
            data.put("code", "2");
            data.put("msg", "解密密钥未获取到");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
        // 数据解密
        String decryptData = null;
        try {
            byte[] bytes = AESCoder.decrypt(paramData.getBytes("utf-8"), key.getBytes("utf-8"));
            decryptData = new String(bytes, "utf-8");
        } catch (Exception e) {
            logger.error("解密发生异常", e);
            data.put("code", "4");
            data.put("msg", "解密异常");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
        if(StringUtils.isBlank(decryptData)){
            data.put("code", "5");
            data.put("msg", "解密后的数据为空");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
        // 数据转换
        List<MessageInfo> messageInfos = (List) JSONObject.toJSON(decryptData);
        List<Message> messages = new ArrayList<>();
        try{
            for(MessageInfo messageInfo : messageInfos){
                Message message = new Message();
                message.setTopic(messageInfo.getTopic());
                message.setTags(messageInfo.getTags());
                message.setKeys(messageInfo.getKeys());
                message.setBody(messageInfo.getMsg().getBytes(RemotingHelper.DEFAULT_CHARSET));
                messages.add(message);
            }
            List<SendResult> sendResults = orderMessageProducer.sendMessages(messages);
            data.put("code", "0");
            data.put("msg", "发送完成");
            data.put("results", sendResults);
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }catch (Exception e){
            logger.error("发送消息发生异常", e);
            data.put("code", "6");
            data.put("msg", "发送消息发生异常");
            return ResponseCode.getInstance(ResponseCodeEnum.SUCCESS, data);
        }
    }
}
