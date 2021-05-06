package com.bootvue.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "menu")
public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 菜单顺序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 菜单唯一key
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * path
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * icon图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 父级菜单id  没有父级为0
     */
    @TableField(value = "p_id")
    private Long pId;

    /**
     * 是否默认选择  0:否   1:是
     */
    @TableField(value = "default_select")
    private Boolean defaultSelect;

    /**
     * 二级菜单是否默认展开   0:否  1:是
     */
    @TableField(value = "default_open")
    private Boolean defaultOpen;
}