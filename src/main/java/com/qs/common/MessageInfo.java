package com.qs.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息定义
 *
 * Created by fbin on 2018/6/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageInfo {

    private String topic;

    private String tags;

    private String keys;

    private String msg;

}
