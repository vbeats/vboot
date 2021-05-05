package com.bootvue.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlatformType {
    WEB(0, "web"),
    WECHAT(1, "wechat");
    private Integer value;
    private String type;
}
