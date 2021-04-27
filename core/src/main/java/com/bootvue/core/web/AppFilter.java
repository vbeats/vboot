package com.bootvue.core.web;

import com.alibaba.fastjson.JSON;
import com.bootvue.core.config.AppConfig;
import com.bootvue.core.constant.AppConst;
import com.bootvue.core.exception.AppException;
import com.bootvue.core.modle.UserInfo;
import com.bootvue.core.util.AppContextHolder;
import com.bootvue.core.util.JwtUtil;
import com.bootvue.core.util.R;
import com.bootvue.core.util.RCode;
import io.jsonwebtoken.Claims;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@WebFilter(filterName = "appFilter", urlPatterns = "/*")
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
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }

            // 校验token

            String token = servletRequest.getHeader(AppConst.TOKEN);
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
