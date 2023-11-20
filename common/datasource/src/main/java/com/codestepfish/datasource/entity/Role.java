package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "`role`")
public class Role {

    @TableId
    private Long id;

    private String name;

    private String key;
}
