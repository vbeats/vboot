package com.bootvue.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@ApiModel("token认证信息")
@AllArgsConstructor
@NoArgsConstructor
public class TokenOut {
    @ApiModelProperty(notes = "token", required = true)
    private String token;

    @ApiModelProperty(notes = "用户名", required = true)
    private String username;
}
