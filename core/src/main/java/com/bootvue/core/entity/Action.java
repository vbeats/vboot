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
@TableName(value = "`action`")
public class Action {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * api path路径
     */
    @TableField(value = "api")
    private String api;

    /**
     * 按钮:list/update/delete/add
     */
    @TableField(value = "`action`")
    private String action;
}