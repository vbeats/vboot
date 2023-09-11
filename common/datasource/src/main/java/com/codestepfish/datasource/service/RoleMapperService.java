package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.Role;
import com.codestepfish.datasource.entity.RoleMenu;
import com.codestepfish.datasource.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleMapperService extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

    private final RoleMenuMapperService roleMenuMapperService;
    private final MenuMapperService menuMapperService;

    public List<Menu> findMenusByRoleId(Long roleId) {
        List<RoleMenu> roleMenus = roleMenuMapperService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));

        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }

        return menuMapperService.list(Wrappers.<Menu>lambdaQuery().in(Menu::getId, roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList())));
    }
}
