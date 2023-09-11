package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminMapperService extends ServiceImpl<AdminMapper, Admin> implements IService<Admin> {

    @Cacheable(cacheNames = "cache:admin#30m#15m#1024", key = "#account", unless = "#result!=null")
    public Admin findAdminByAccount(String account) {
        return getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAccount, account));
    }
}
