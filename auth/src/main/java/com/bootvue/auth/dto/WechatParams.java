package com.bootvue.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel(description = "微信小程序相关参数")
public class WechatParams {

    @ApiModelProperty(notes = "小程序端login code")
    private String code;
    private String nickName;
    private Integer gender;
    private String avatarUrl;
    private String province;
    private String country;
    private String city;

    /*加密数据*/
    private String iv;
    private String encryptedData;
    private String rawData;
    private String signature;
}
