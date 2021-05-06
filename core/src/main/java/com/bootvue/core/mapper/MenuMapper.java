package com.bootvue.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootvue.core.ddo.MenuDo;
import com.bootvue.core.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuDo> getMenuList(@Param("user_id") Long userId, @Param("role_id") Long roleId, @Param("p_id") Long menuPid);
}