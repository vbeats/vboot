package com.bootvue.core.constant;

public class AppConst {
    public static final String CAPTCHA_KEY = "captcha:line_%s";
    public static final String SMS_KEY = "code:sms_%s";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    //    --------------------正则
    // 手机号
    public static final String PHONE_REGEX = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";

    // -----------------------url
    public static final String WECHAT_CODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

}
