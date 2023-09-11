package com.codestepfish.datasource.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {

    WEB("web", "平台web端"),
    APP("app", "app移动端"),
    CASHIER("cashier", "收银台");

    @JsonValue
    private String type;
    private String desc;

}
