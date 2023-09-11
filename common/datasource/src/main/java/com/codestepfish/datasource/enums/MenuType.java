package com.codestepfish.datasource.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum MenuType {
    MENU(0, "菜单"),
    BUTTON(1, "按钮"),
    ;

    @EnumValue
    private Integer value;
    private String type;

}
