package com.bootvue.auth.dto;

import com.bootvue.core.constant.AppConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(description = "获取短信验证码参数")
public class PhoneParams {
    @ApiModelProperty(notes = "手机号", required = true)
    @Pattern(regexp = AppConst.PHONE_REGEX, message = "手机号错误")
    private String phone;

    @ApiModelProperty(notes = "租户编号,默认000000", required = true)
    @NotEmpty(message = "租户编号不能为空")
    private String tenantCode;
}
