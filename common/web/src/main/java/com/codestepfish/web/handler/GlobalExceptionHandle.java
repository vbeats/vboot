package com.codestepfish.web.handler;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.codestepfish.core.exception.AppException;
import com.codestepfish.core.model.R;
import com.codestepfish.core.model.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.BindException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public <T> R<T> handleException(AppException e) {
        log.error("拦截到业务异常: ", e);
        return new R<>(e.getCode(), e.getMsg(), null);
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public <T> R<T> handleException(IllegalArgumentException e) {
        log.error("拦截到IllegalArgumentException异常: ", e);
        return R.error(new AppException(RCode.PARAM_ERROR.getCode(), e.getMessage()));
    }

    @ExceptionHandler(value = SaTokenException.class)
    @ResponseBody
    public <T> R<T> handleException(SaTokenException e) {
        log.error("拦截到认证/权限异常: ", e);
        if (e instanceof NotRoleException || e instanceof NotPermissionException) {
            return R.error(new AppException(RCode.ACCESS_DENY));
        } else {
            return R.error(new AppException(RCode.UNAUTHORIZED_ERROR));
        }
    }


    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseBody
    public <T> R<T> handleException(MissingServletRequestParameterException e) {
        log.error("拦截到参数异常: ", e);
        return R.error(new AppException(RCode.PARAM_ERROR));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> R<T> handleException(Exception e) {
        log.error("拦截到未知异常: ", e);
        if (e instanceof HttpMessageConversionException || e instanceof BindException) {
            return R.error(new AppException(RCode.PARAM_ERROR));
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return R.error(RCode.PARAM_ERROR.getCode(), "请求方式错误");
        }
        return R.error(new AppException(RCode.DEFAULT.getCode(), RCode.DEFAULT.getMsg()));
    }

}
