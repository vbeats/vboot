package com.codestepfish.auth.util;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.codestepfish.auth.model.LoginUser;
import com.codestepfish.core.constant.AppConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    public static void login(LoginUser loginUser, SaLoginModel model) {

        model = ObjectUtil.defaultIfNull(model, new SaLoginModel());
        // 塞点额外数据
        model.setExtra(AppConstants.USER, loginUser);

        StpUtil.login(loginUser.getId(), model);
    }


    public static LoginUser getLoginUser() {
        return (LoginUser) StpUtil.getExtra(AppConstants.USER);
    }
}
