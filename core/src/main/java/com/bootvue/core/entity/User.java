package com.bootvue.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("`user`")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;  // 用户名

    @TableField(value = "`password`")
    private String password;  // 密码

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}
