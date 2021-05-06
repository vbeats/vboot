package com.bootvue.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootvue.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select u.* from `user` u, tenant t where u.tenant_id = t.id and u.username = #{username} and u.password = #{password} and u.status = 1 and t.code = #{tenant_code} and u.delete_time is null")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password, @Param("tenant_code") String tenantCode);

    @Select("select u.* from `user` u, tenant t where u.tenant_id = t.id and u.username = #{phone} and u.status = 1 and t.code = #{tenant_code} and u.delete_time is null")
    User findByPhone(@Param("phone") String phone, @Param("tenant_code") String tenantCode);

    @Select("select u.* from user u, tenant t where u.tenant_id = t.id and u.openid=#{openid} and u.status=1 and t.code=#{tenant_code} and u.delete_time is null")
    User findByOpenid(@Param("openid") String openid, @Param("tenant_code") String tenantCode);
}