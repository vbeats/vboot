package com.codestepfish.auth.provider;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.model.LoginUser;
import com.codestepfish.auth.util.AuthUtil;
import com.codestepfish.core.constant.AppConstants;
import com.codestepfish.core.exception.AppException;
import com.codestepfish.core.model.RCode;
import com.codestepfish.core.util.ValidatorUtils;
import com.codestepfish.core.validate.auth.PasswordGroup;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Client;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.Role;
import com.codestepfish.datasource.service.AdminMapperService;
import com.codestepfish.datasource.service.ClientMapperService;
import com.codestepfish.datasource.service.RoleMapperService;
import com.codestepfish.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号 密码认证
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountPasswordProvider implements AuthProvider {

    private final ClientMapperService clientMapperService;
    private final AdminMapperService adminMapperService;
    private final RoleMapperService roleMapperService;

    @Override
    public AuthResponse handleAuth(AuthParam param) {

        // 校验参数
        ValidatorUtils.validate(param, PasswordGroup.class);

        // client
        Client client = clientMapperService.findClientByClientId(param.getClientId());

        if (ObjectUtils.isEmpty(client) || !client.getGrantType().contains(param.getGrantType().getType()) || !client.getSecret().equals(param.getSecret())) {
            throw new AppException(RCode.PARAM_ERROR.getCode(), "client不匹配");
        }

        // 验证码
        String captchaKey = String.format(AppConstants.CAPTCHA_KEY, param.getKey());
        String code = RedisUtil.get(captchaKey);
        RedisUtil.delete(captchaKey);
        if (!StringUtils.hasText(code) || !code.equals(param.getCode())) {
            throw new AppException(RCode.PARAM_ERROR.getCode(), "验证码错误");
        }

        // 账号密码
        Admin admin = adminMapperService.findAdminByAccount(param.getAccount());
        if (ObjectUtils.isEmpty(admin) || !StringUtils.hasText(admin.getPassword()) || !DigestUtils.md5Hex(String.format(AppConstants.PASSWORD_RULE, param.getAccount(), param.getPassword())).equalsIgnoreCase(admin.getPassword())) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR.getCode(), "账号/密码错误");
        }

        Assert.isTrue(admin.getStatus(), "账号已被禁用");

        // 角色
        Role role = roleMapperService.getById(admin.getRoleId());

        // 菜单权限
        List<Menu> menus = roleMapperService.findMenusByRoleId(admin.getRoleId());

        // token
        SaLoginModel model = new SaLoginModel()
                .setDevice(client.getDeviceType())
                .setActiveTimeout(client.getActiveTimeout())
                .setTimeout(client.getTimeout())
                .setExtra(AppConstants.CLIENT_ID, client.getClientId())
                .setExtra(AppConstants.ROLE_KEY, role.getKey())
                .setExtra(AppConstants.PERMS, menus.stream().map(Menu::getPerms).collect(Collectors.toList()));

        AuthUtil.login(BeanUtil.copyProperties(admin, LoginUser.class), model);

        return new AuthResponse(StpUtil.getTokenValue(), StpUtil.getTokenTimeout());
    }

    @Override
    public AuthResponse refreshToken(AuthParam param) {

        // client
        Client client = clientMapperService.findClientByClientId(param.getClientId());

        if (ObjectUtils.isEmpty(client) || !client.getGrantType().contains(param.getGrantType().getType()) || !client.getSecret().equals(param.getSecret())) {
            throw new AppException(RCode.PARAM_ERROR.getCode(), "client不匹配");
        }

        StpUtil.renewTimeout(client.getTimeout());

        return new AuthResponse(StpUtil.getTokenValue(), StpUtil.getTokenTimeout());
    }
}
