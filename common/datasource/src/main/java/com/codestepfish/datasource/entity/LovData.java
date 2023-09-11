package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "lov_data")
public class LovData {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String category;

    @TableField(value = "`key`")
    private String key;

    private String value;

    private String remark;
}
