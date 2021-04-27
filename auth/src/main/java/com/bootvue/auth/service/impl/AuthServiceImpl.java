package com.bootvue.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootvue.auth.service.AuthService;
import com.bootvue.auth.vo.CaptchaResponse;
import com.bootvue.auth.vo.Credentials;
import com.bootvue.auth.vo.TokenOut;
import com.bootvue.core.config.AppConfig;
import com.bootvue.core.constant.AppConst;
import com.bootvue.core.entity.User;
import com.bootvue.core.exception.AppException;
import com.bootvue.core.mapper.UserMapper;
import com.bootvue.core.modle.Token;
import com.bootvue.core.util.JwtUtil;
import com.bootvue.core.util.RCode;
import com.bootvue.core.util.RsaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    private static final LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

    private final UserMapper userMapper;
    private final RedissonClient redissonClient;
    private final AppConfig appConfig;

    @Override
    public TokenOut token(Credentials credentials) {
        // 1. 验证码
        RBucket<String> code = redissonClient.getBucket(String.format(AppConst.CAPTCHA_KEY, credentials.getKey()));
        if (!credentials.getCode().equalsIgnoreCase(code.get())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        // 2. 校验密码
        String decryptPassword = RsaUtil.decrypt(appConfig.getPrivateKey(), credentials.getPassword());
        if (!StringUtils.hasText(decryptPassword)) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getUsername, credentials.getUsername()).eq(User::getPassword, DigestUtils.md5Hex(decryptPassword))
                .isNull(User::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(user)) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        code.delete();
        // token
        Token token = new Token(user.getId(), user.getUsername());

        return new TokenOut(JwtUtil.encode(LocalDateTime.now().plusHours(8L), BeanUtil.beanToMap(token)), user.getUsername());
    }

    @Override
    public CaptchaResponse createCaptcha() {
        lineCaptcha.createCode();
        String code = lineCaptcha.getCode();
        String key = RandomStringUtils.randomAlphanumeric(12);
        String image = "data:image/png;base64," + lineCaptcha.getImageBase64();
        RBucket<String> bucket = redissonClient.getBucket(String.format(AppConst.CAPTCHA_KEY, key));
        bucket.set(code, 10, TimeUnit.MINUTES);

        return new CaptchaResponse(key, image);
    }
}
