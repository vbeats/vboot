package com.bootvue.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum AuthType {
    REFRESH_TOKEN(0, "refresh_token"),
    USERNAME_PASSWORD_LOGIN(1, "username_password_login"),
    SMS_LOGIN(2, "sms_login"),
    WECHAT(3, "wechat");
    private Integer value;
    private String type;
}
