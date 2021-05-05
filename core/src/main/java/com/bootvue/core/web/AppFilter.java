package com.bootvue.core.web;

import com.alibaba.fastjson.JSON;
import com.bootvue.core.config.app.AppConfig;
import com.bootvue.core.config.app.Key;
import com.bootvue.core.exception.AppException;
import com.bootvue.core.modle.UserInfo;
import com.bootvue.core.result.R;
import com.bootvue.core.result.RCode;
import com.bootvue.core.util.AppContextHolder;
import com.bootvue.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@WebFilter
@Component
public class AppFilter implements Filter {
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private final AppConfig appConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            // 放行
            if (!CollectionUtils.isEmpty(appConfig.getSkipUrls())) {
                String path = servletRequest.getRequestURI();

                for (String skipUrl : appConfig.getSkipUrls()) {
                    if (PATH_MATCHER.match(skipUrl, path)) {
                        if (path.startsWith("/auth")) {
                            // 检查客户端Key
                            String appid = servletRequest.getParameter("appid");
                            String secret = servletRequest.getParameter("secret");
                            if (!StringUtils.hasText(appid) || !StringUtils.hasText(secret)) {
                                throw new AppException(RCode.PARAM_ERROR.getCode(), "客户端参数无效");
                            }
                            Key key = appConfig.getAuthKey().stream()
                                    .filter(it -> it.getAppId().equals(appid)
                                            && it.getSecret().equals(secret))
                                    .findAny().orElse(null);

                            if (ObjectUtils.isEmpty(key)) {
                                throw new AppException(RCode.PARAM_ERROR.getCode(), "客户端参数无效");
                            }
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }

            // 校验token

            String token = servletRequest.getHeader("token");
            if (!StringUtils.hasText(token) || !JwtUtil.isVerify(token)) {
                throw new AppException(RCode.UNAUTHORIZED_ERROR);
            }

            Claims claims = JwtUtil.decode(token);

            AppContextHolder.setUserInfo(
                    new UserInfo(claims.get("userId", Long.class),
                            claims.get("username", String.class))
            );
            chain.doFilter(request, response);
        } catch (AppException e) {
            handleResponse(e.getCode(), e.getMsg(), servletResponse);
        } catch (Exception e) {
            handleResponse(RCode.DEFAULT.getCode(), RCode.DEFAULT.getMsg(), servletResponse);
        } finally {
            AppContextHolder.remove();
        }
    }

    private void handleResponse(Integer code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding(CharsetUtil.UTF_8.toString());
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(R.error(code, msg)));
        writer.flush();
    }
}
