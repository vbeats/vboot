package com.bootvue.auth.service;

import com.bootvue.auth.vo.AuthResponse;
import com.bootvue.auth.vo.CaptchaResponse;
import com.bootvue.auth.vo.Credentials;
import com.bootvue.auth.vo.PhoneParams;

public interface AuthService {
    AuthResponse authentication(Credentials credentials);

    CaptchaResponse createCaptcha();

    void handleSmsCode(PhoneParams phoneParams);
}
