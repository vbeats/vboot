package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "lov")
public class Lov {

    @TableId
    private Long id;

    @TableField(value = "`key`")
    private String key;

    private String value;

    private String remark;
}
