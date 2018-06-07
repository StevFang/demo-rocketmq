package com.qs;

import com.qs.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统级缓存加载
 *
 * Created by fbin on 2018/6/7.
 */
public class CacheInit implements InitializingBean {

    /**
     * 记录日志
     */
    private static Logger logger = LoggerFactory.getLogger(CacheInit.class);

    private static Map<String, Object> keyMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public static String getKey(String appId){
        if(keyMap.containsKey(appId)){
            return ConvertUtil.getStr(keyMap.get(appId));
        }else{
            return null;
        }
    }

}
