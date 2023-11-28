package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.datasource.enums.MenuType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "menu")
public class Menu {

    @TableId
    private Long id;

    private Long pid;

    private String title;

    private String path;

    private String icon;

    @TableField(value = "`key`")
    private String key;

    private String perms;

    @TableField(value = "`type`")
    private MenuType type;

    private Integer orderNum;

    private String remark;
}
