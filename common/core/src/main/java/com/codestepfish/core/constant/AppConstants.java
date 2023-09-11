package com.codestepfish.core.constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConstants {

    // *****************sa-token *********************
    public static final String DEPT_IDS = "deptIds";  // 所属 机构 & 所有子级 id集合
    public static final String USER = "user";
    public static final String CLIENT_ID = "clientId";
    public static final String ROLE_KEY = "role";
    public static final String PERMS = "perms";  // 菜单权限


    // ***************** 账号 角色相关**************************

    public static final String PASSWORD_RULE = "%s:*:%s";  // 默认密码规则 account:*:password

    public static final Long SUPER_ADMIN_ROLE_ID = 1L;  // 超级管理员 role_id
    public static final String SUPER_ADMIN_ROLE_KEY = "superadmin";  // 超级管理员 role key


    // ****************** redis key ***************************
    public static final String CAPTCHA_KEY = "code:captcha:%s";  // 图形验证码

    public static final String CACHE_CLIENT = "cache:client"; // client cache
}
