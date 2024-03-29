package com.codestepfish.web.aop;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.codestepfish.core.util.AppContextHolder;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * request 请求 log
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogAspect {

    private static final List<String> WORDS = Arrays.asList("authorization", "secret", "password", "newPassword");
    private final Environment env;

    @Around("execution(* com.codestepfish..*Controller.*(..)) || @within(org.springframework.web.bind.annotation.RestController))")
    public Object logParam(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Method method = ms.getMethod();
        Object[] args = point.getArgs();

        StringBuffer sb = new StringBuffer();
        sb.append("\n***************************** Request Start *****************************\n");
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            sb.append("Service: " + env.getProperty("spring.application.name", "Unknown") + "\n");

            //uri
            sb.append("Uri: " + request.getRequestURI() + "\n");

            //method
            sb.append("Method: " + request.getMethod() + "\n");

            // headers
            sb.append("Headers: " + handleHeaders(request) + "\n");

            // queryString 参数
            sb.append("QueryString: " + handleQueryString(request.getQueryString()) + "\n");

            // parameter 参数
            sb.append("Parameter: " + handleParameter(request.getParameterMap()) + "\n");

            // body 参数
            if (!Arrays.asList("GET", "DELETE").contains(request.getMethod())) {
                sb.append("Body: " + handleBody(args) + "\n");
            }

            // Controller
            sb.append("Controller: " + method.toString() + "\n");

            Object postArgs = point.proceed();

            // 响应
            sb.append("Response: " + handleResponse(postArgs) + "\n");

            return postArgs;
        } finally {
            AppContextHolder.clear();
            sb.append("Cost: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms\n");
            sb.append("***************************** Request End *******************************\n");
            log.info("{}", sb);
        }
    }

    private String handleResponse(Object postArgs) {
        if (ObjectUtils.isEmpty(postArgs)) {
            return null;
        }

        if (postArgs instanceof byte[]) {
            return JSON.toJSONString(new String((byte[]) postArgs));
        } else {
            return JSON.toJSONString(postArgs);
        }
    }

    private String handleHeaders(HttpServletRequest request) {
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        class Header {
            private String key;
            private String value;
        }
        List<Header> headers = new ArrayList<>();
        // 脱敏
        request.getHeaderNames().asIterator().forEachRemaining(s -> {
            String header = request.getHeader(s);
            String value = WORDS.contains(s.toLowerCase()) && StringUtils.hasText(header) ? StrUtil.hide(header, 1, header.length()) : header;

            headers.add(new Header(s, value));
        });

        return JSON.toJSONString(headers);
    }

    // queryString
    private String handleQueryString(String queryString) {
        if (!StringUtils.hasText(queryString)) {
            return "";
        }

        List<String> queryStringList = Splitter.on("&").trimResults().omitEmptyStrings().splitToList(queryString);
        return JSON.toJSONString(queryStringList);
    }

    // parameter
    private String handleParameter(Map<String, String[]> parameterMap) {
        if (CollectionUtils.isEmpty(parameterMap)) {
            return "";
        }

        return JSON.toJSONString(parameterMap);
    }

    // body
    private String handleBody(Object[] args) {
        List<Object> objects = Arrays.asList(args);
        if (CollectionUtils.isEmpty(objects)) {
            return "";
        }

        Map<String, Object> params = new HashMap<>();
        objects.forEach(e -> {
            if (e instanceof MultipartFile file) {
                params.put(file.getName(), file.getOriginalFilename() + "  " + file.getSize());
            } else if (e instanceof BindingResult || e instanceof HttpServletRequest || e instanceof HttpServletResponse) {
                // 不处理
            } else {
                // 脱敏
                BeanUtil.beanToMap(e, params, CopyOptions.create().ignoreNullValue().setFieldValueEditor((key, value) ->
                        WORDS.contains(key.toLowerCase()) && !ObjectUtils.isEmpty(value) ? StrUtil.hide(String.valueOf(value), 1, String.valueOf(value).length()) : value));
            }
        });

        return JSON.toJSONString(params);
    }

}
