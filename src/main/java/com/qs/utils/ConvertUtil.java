package com.qs.utils;

/**
 * Created by fbin on 2018/6/7.
 */
public class ConvertUtil {

    /**
     * 获取字符串
     * @param obj
     * @return
     */
    public static String getStr(Object obj){
        if(obj == null) {
            return null;
        }
        if(obj instanceof String) {
            return (String)obj;
        }
        return String.valueOf(obj);
    }

}
