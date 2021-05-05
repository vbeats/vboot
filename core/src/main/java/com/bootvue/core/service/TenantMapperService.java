package com.bootvue.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootvue.core.entity.Tenant;
import com.bootvue.core.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantMapperService {
    private final TenantMapper tenantMapper;

    public Tenant findByTenantCode(String tenantCode) {
        return tenantMapper.selectOne(new QueryWrapper<Tenant>().lambda()
                .eq(Tenant::getCode, tenantCode)
        );
    }
}
