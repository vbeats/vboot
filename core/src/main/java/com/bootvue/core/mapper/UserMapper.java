package com.bootvue.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootvue.core.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password, @Param("tenant_code") String tenantCode);

    User findByPhone(@Param("phone") String phone, @Param("tenant_code") String tenantCode);

    User findByOpenid(@Param("openid") String openid, @Param("tenant_code") String tenantCode);
}