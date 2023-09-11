package com.codestepfish.auth.model;

import com.codestepfish.datasource.enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class LoginUser implements Serializable {

    private Long id; // 用户id

    private String account;

    private String phone;

    private AccountType type;

    private Long roleId;

    private Long deptId;
}
