package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "role_menu")
public class RoleMenu {

    @TableId
    private Long id;

    private Long roleId;

    private Long menuId;
}
