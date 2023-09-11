package com.codestepfish.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.provider.AuthProviderContextHolder;
import com.codestepfish.core.model.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SaIgnore
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthEndpoint {
    @Operation(summary = "获取token")
    @PostMapping("/token")
    public R<AuthResponse> token(@Valid @RequestBody AuthParam param) {
        return R.success(AuthProviderContextHolder.getAuthProvider(param.getGrantType()).handleAuth(param));
    }

    @SaCheckLogin
    @Operation(summary = "刷新token 有效时长")
    @PostMapping("/refresh_token")
    public R<AuthResponse> refreshToken(@Valid @RequestBody AuthParam param) {
        return R.success(AuthProviderContextHolder.getAuthProvider(param.getGrantType()).refreshToken(param));
    }
}
