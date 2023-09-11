package com.codestepfish.datasource.vo;

import com.codestepfish.core.annotation.Sensitive;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.enums.AccountType;
import com.codestepfish.core.enums.SensitiveStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AutoMapper(target = Admin.class)
public class AdminVo {

    private Long id;

    private String account;

    @JsonIgnore
    private String password;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;

    private AccountType type;

    private Long roleId;

    private Long deptId;

    private Boolean status;

    private Boolean delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String roleName;

    private String deptName;

}
