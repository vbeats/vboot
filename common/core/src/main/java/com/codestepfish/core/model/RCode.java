package com.codestepfish.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RCode {
    SUCCESS(200, "Success"),
    PARAM_ERROR(400, "Bad Request"),
    UNAUTHORIZED_ERROR(401, "Unauthorized"),
    ACCESS_DENY(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    DEFAULT(500, "System Error"),
    ;

    private final Integer code;
    private final String msg;
}
