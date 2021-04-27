package com.bootvue.core.exception;

import com.bootvue.core.util.RCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException {
    private Integer code;
    private String msg;

    public AppException(RCode rCode) {
        this.code = rCode.getCode();
        this.msg = rCode.getMsg();
    }
}
