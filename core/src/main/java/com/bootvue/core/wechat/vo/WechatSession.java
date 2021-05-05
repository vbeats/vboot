package com.bootvue.core.wechat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WechatSession {
    private String openid;
    private String sessionKey;
    private String unionid;
    private Integer errcode;  // 他娘的  怎么不返回这个字段  文档写了些屁
    private String errmsg;
}
