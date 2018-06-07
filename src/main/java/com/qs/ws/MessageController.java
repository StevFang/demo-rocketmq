package com.qs.ws;

import com.qs.common.ResponseCodeEnum;
import com.qs.common.ResponseCode;
import com.qs.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by fbin on 2018/6/7.
 */
@RestController
@Scope("prototype")
@RequestMapping("/message")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource(name = "messageService")
    private MessageService messageService;

    @ResponseBody
    @RequestMapping(value = "send", method = { RequestMethod.POST })
    public ResponseCode sendSingleMessage(HttpServletRequest request,
                                          @RequestParam("appId") String appId,
                                          @RequestParam("paramData") String paramData){
        try{
            return messageService.sendMessage(appId, paramData);
        }catch (Exception e){
            logger.error("未知异常", e);
            return ResponseCode.getInstance(ResponseCodeEnum.ERROR, e.getMessage());
        }
    }

}
