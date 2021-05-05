package com.bootvue.core.config.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Key {
    private String appId;
    private String secret;
    private Integer platform; // 0 web端  1 wechat小程序端
    // rsa密钥对
    private String publicKey;
    private String privateKey;
    // 微信appid
    private String wechatAppId;
    // 微信secret
    private String wechatSecret;
}
