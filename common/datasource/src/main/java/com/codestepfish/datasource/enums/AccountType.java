package com.codestepfish.datasource.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum AccountType {
    ADMIN(0, "平台管理员"),
    AGENT(1, "机构代理"),
    MERCHANT(2, "商户"),
    EMPLOYEE(3, "员工");

    @EnumValue
    private Integer value;
    private String type;

}
