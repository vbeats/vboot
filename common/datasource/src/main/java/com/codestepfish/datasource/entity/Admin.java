package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.codestepfish.datasource.enums.AccountType;
import com.codestepfish.core.constant.AppConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName(value = "admin")
public class Admin implements Serializable {

    @TableId
    private Long id;

    private String account;

    @TableField(
            insertStrategy = FieldStrategy.NOT_EMPTY,
            updateStrategy = FieldStrategy.NOT_EMPTY,
            whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    private String phone;

    @TableField(value = "`type`")
    private AccountType type;

    private Long roleId;

    private Long deptId;

    @TableField(value = "`status`")
    private Boolean status;

    @TableLogic
    private Boolean delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // ---------------------------------------------------
    public boolean isSuperAdmin() {
        return AppConstants.SUPER_ADMIN_ROLE_ID.equals(roleId);
    }
}
