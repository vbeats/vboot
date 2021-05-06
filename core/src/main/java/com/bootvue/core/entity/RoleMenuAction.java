package com.bootvue.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "role_menu_action")
public class RoleMenuAction implements Serializable {
    private static final long serialVersionUID = 5003718427400535244L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * menu id
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * action id ,分隔 0表示可以查看菜单(list) -1不可以查看
     */
    @TableField(value = "action_ids")
    private String actionIds;
}