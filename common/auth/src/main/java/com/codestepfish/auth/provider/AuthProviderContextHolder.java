package com.codestepfish.auth.provider;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.core.exception.AppException;
import com.codestepfish.core.model.RCode;
import com.codestepfish.datasource.enums.GrantType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthProviderContextHolder {
    public static AuthProvider getAuthProvider(GrantType grantType) {
        return switch (grantType) {
            case PASSWORD -> SpringUtil.getBean(AccountPasswordProvider.class);
            case WX_MA -> SpringUtil.getBean(WxMaAuthProvider.class);
            default -> {
                log.error("不支持的认证类型: {}", grantType);
                throw new AppException(RCode.PARAM_ERROR.getCode(), "认证类型错误");
            }
        };
    }
}
