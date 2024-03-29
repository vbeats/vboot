package com.codestepfish.auth.provider;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;

public interface AuthProvider {
    /**
     * 用户认证
     *
     * @param param
     * @return
     */
    AuthResponse handleAuth(AuthParam param);

    /**
     * 延长token有效期
     *
     * @param param
     * @return
     */
    AuthResponse refreshToken(AuthParam param);
}
