package com.bootvue.core.exception;

import com.bootvue.core.util.R;
import com.bootvue.core.util.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public <T> R<T> handleException(AppException e) {
        return new R<>(e.getCode(), e.getMsg(), null);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> R<T> handleException(Exception e) {
        log.error("拦截到未知异常: ", e);
        return R.error(new AppException(RCode.DEFAULT.getCode(), RCode.DEFAULT.getMsg()));
    }

}
