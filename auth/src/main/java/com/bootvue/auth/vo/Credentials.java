package com.bootvue.auth.vo;

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

    @ApiModelProperty(notes = "用户名", required = true)
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(notes = "密码 RSA公钥加密", required = true)
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "图形验证码", required = true)
    @NotNull(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(notes = "图形验证码的key", required = true)
    @NotNull(message = "验证码key不能为空")
    private String key;

}
