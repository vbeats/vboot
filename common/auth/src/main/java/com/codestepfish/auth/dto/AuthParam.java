package com.codestepfish.auth.dto;

import com.codestepfish.core.validate.auth.PasswordGroup;
import com.codestepfish.core.validate.auth.WechatGroup;
import com.codestepfish.datasource.enums.GrantType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(name = "认证参数", description = "认证参数")
public class AuthParam {

    @Schema(description = "clientId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "clientId不能为空")
    private String clientId;

    @Schema(description = "secret", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "secret不能为空")
    private String secret;

    @Schema(description = "认证类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "认证类型不能为空")
    private GrantType grantType;

    @NotBlank(message = "账号不能为空", groups = {PasswordGroup.class})
    private String account;

    @NotBlank(message = "密码不能为空", groups = {PasswordGroup.class})
    private String password;

    @NotBlank(message = "验证码key不能为空", groups = {PasswordGroup.class})
    private String key;

    @NotBlank(message = "code不能为空", groups = {PasswordGroup.class, WechatGroup.class})
    private String code;

    @NotBlank(message = "appid不能为空", groups = {WechatGroup.class})
    private String appid;

}
