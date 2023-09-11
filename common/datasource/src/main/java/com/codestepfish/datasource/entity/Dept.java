package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.datasource.config.JsonObjectTypeHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@TableName(value = "`dept`", autoResultMap = true)
public class Dept {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long pid;

    @TableField(value = "pids", typeHandler = JsonObjectTypeHandler.class)
    private List<Long> pids;

    private Integer orderNum;

    private String leader;

    private String phone;
}
