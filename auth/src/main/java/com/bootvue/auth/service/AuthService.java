package com.bootvue.auth.service;

import com.bootvue.auth.vo.CaptchaResponse;
import com.bootvue.auth.vo.Credentials;
import com.bootvue.auth.vo.TokenOut;

public interface AuthService {
    TokenOut token(Credentials credentials);

    CaptchaResponse createCaptcha();
}
