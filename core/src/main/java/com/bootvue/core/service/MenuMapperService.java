package com.bootvue.core.service;

import com.bootvue.core.ddo.MenuDo;
import com.bootvue.core.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuMapperService {
    private final MenuMapper menuMapper;

    /**
     * 获取菜单
     *
     * @param userId  用户id
     * @param roleId  角色id
     * @param menuPid menu菜单父级id
     * @return
     */
    public List<MenuDo> getMenuList(Long userId, Long roleId, Long menuPid) {
        return menuMapper.getMenuList(userId, roleId, menuPid);
    }
}
