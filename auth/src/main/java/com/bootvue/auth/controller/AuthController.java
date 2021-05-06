package com.bootvue.auth.controller;

import com.bootvue.auth.service.AuthService;
import com.bootvue.auth.dto.AuthResponse;
import com.bootvue.auth.dto.CaptchaResponse;
import com.bootvue.auth.dto.Credentials;
import com.bootvue.auth.dto.PhoneParams;
import com.bootvue.core.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "用户认证相关接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    @ApiOperation("用户认证 获取token/刷新token")
    private AuthResponse authentication(@RequestBody @Valid Credentials credentials, BindingResult result) {
        R.handleErr(result);
        return authService.authentication(credentials);
    }

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public CaptchaResponse captcha() {
        return authService.createCaptcha();
    }

    @ApiOperation("获取短信验证码")
    @PostMapping("/sms")
    public void smsCode(@RequestBody @Valid PhoneParams phoneParams, BindingResult result) {
        R.handleErr(result);
        authService.handleSmsCode(phoneParams);
    }

}
