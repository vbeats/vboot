package com.bootvue.auth.service;

import com.bootvue.auth.dto.AuthResponse;
import com.bootvue.auth.dto.CaptchaResponse;
import com.bootvue.auth.dto.Credentials;
import com.bootvue.auth.dto.PhoneParams;

public interface AuthService {
    AuthResponse authentication(Credentials credentials);

    CaptchaResponse createCaptcha();

    void handleSmsCode(PhoneParams phoneParams);
}
