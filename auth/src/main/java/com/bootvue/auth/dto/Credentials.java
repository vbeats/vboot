package com.bootvue.auth.dto;

import com.bootvue.core.constant.AuthType;
import com.bootvue.core.constant.PlatformType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@ApiModel(description = "登录相关凭证")
public class Credentials {

    @ApiModelProperty(notes = "租户编号")
    private String tenantCode;

    @ApiModelProperty(notes = "用户名")
    private String username;

    @ApiModelProperty(notes = "密码 RSA公钥加密")
    private String password;

    @ApiModelProperty(notes = "手机号")
    private String phone;

    @ApiModelProperty(notes = "图形验证码或短信验证码")
    private String code;

    @ApiModelProperty(notes = "图形验证码的key")
    private String key;

    @ApiModelProperty(notes = "认证方式 0: 换取新的access_token与refresh_token 1: 用户名密码登录 2: 短信登录 3: 微信小程序认证", required = true)
    @NotNull(message = "认证方式不能为空")
    private AuthType type;

    @ApiModelProperty(notes = "客户端平台类型 0:web 1:微信小程序", required = true)
    private PlatformType platform;

    @ApiModelProperty(notes = "旧的refresh_token")
    private String refreshToken;

    @ApiModelProperty(notes = "微信小程序相关参数")
    private WechatParams wechat;
}
