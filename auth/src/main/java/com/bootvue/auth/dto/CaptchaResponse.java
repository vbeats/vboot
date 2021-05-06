package com.bootvue.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@ApiModel("图形验证码")
public class CaptchaResponse implements Serializable {
    private static final long serialVersionUID = -8913185071017742461L;

    @ApiModelProperty(notes = "唯一key", required = true)
    private String key;
    @ApiModelProperty(notes = "base64 图形数据", required = true)
    private String image;
}