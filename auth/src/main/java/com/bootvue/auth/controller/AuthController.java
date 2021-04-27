package com.bootvue.auth.controller;

import com.bootvue.auth.service.AuthService;
import com.bootvue.auth.vo.CaptchaResponse;
import com.bootvue.auth.vo.Credentials;
import com.bootvue.auth.vo.TokenOut;
import com.bootvue.core.util.R;
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
    @ApiOperation("获取token")
    private TokenOut token(@RequestBody @Valid Credentials credentials, BindingResult result) {
        R.handleErr(result);
        return authService.token(credentials);
    }

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public CaptchaResponse captcha() {
        return authService.createCaptcha();
    }

}
