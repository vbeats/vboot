package com.bootvue.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootvue.core.entity.User;
import com.bootvue.core.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapperService {
    private final UserMapper userMapper;

    /**
     * 用户id 查询用户
     *
     * @param id user id
     * @return user
     */
    @Cacheable(cacheNames = "cache:user", key = "#id", unless = "#result == null")
    public User findById(Long id) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getId, id).eq(User::getStatus, true)
                .isNull(User::getDeleteTime));
    }

    public User findByUsernameAndPassword(String username, String password, String tenantCode) {
        return userMapper.findByUsernameAndPassword(username, password, tenantCode);
    }

    /**
     * 手机号查询user
     *
     * @param phone      手机号
     * @param tenantCode 租户编号
     * @return user
     */
    public User findByPhone(String phone, String tenantCode) {
        return userMapper.findByPhone(phone, tenantCode);
    }


    /**
     * openid查询user  普通用户角色
     *
     * @param openid     openid
     * @param tenantCode 租户编号
     * @return user
     */
    public User findByOpenid(String openid, String tenantCode) {
        return userMapper.findByOpenid(openid, tenantCode);
    }
}
