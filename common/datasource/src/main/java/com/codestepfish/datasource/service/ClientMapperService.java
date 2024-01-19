package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.core.constant.AppConstants;
import com.codestepfish.datasource.entity.Client;
import com.codestepfish.datasource.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientMapperService extends ServiceImpl<ClientMapper, Client> implements IService<Client> {

    @Cacheable(cacheNames = AppConstants.CACHE_CLIENT, key = "#clientId", unless = "#result==null")
    public Client findClientByClientId(String clientId) {
        return getOne(Wrappers.<Client>lambdaQuery().eq(Client::getClientId, clientId));
    }
}
