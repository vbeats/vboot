package com.codestepfish.core.exception;

import com.codestepfish.core.model.RCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppException extends RuntimeException {
    private Integer code;
    private String msg;

    public AppException(RCode rCode) {
        this.code = rCode.getCode();
        this.msg = rCode.getMsg();
    }
}
