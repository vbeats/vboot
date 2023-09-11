package com.codestepfish.datasource.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrantType {

    WX_MA("wx_ma", "微信小程序"),
    WX_MP("wx_mp", "微信公众平台"),
    WX_CP("wx_cp", "企业微信"),
    ALI_MINI_APP("ali_mini_app", "支付宝小程序"),
    PASSWORD("password", "web端账号密码登陆"),
    SMS("sms", "web端短信登陆"),
    ;

    @JsonValue
    private String type;
    private String desc;

}
