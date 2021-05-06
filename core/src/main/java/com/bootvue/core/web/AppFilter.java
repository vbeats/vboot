package com.bootvue.core.web;

import com.alibaba.fastjson.JSON;
import com.bootvue.core.config.app.AppConfig;
import com.bootvue.core.config.app.Key;
import com.bootvue.core.entity.Action;
import com.bootvue.core.entity.RoleMenuAction;
import com.bootvue.core.exception.AppException;
import com.bootvue.core.modle.UserInfo;
import com.bootvue.core.result.R;
import com.bootvue.core.result.RCode;
import com.bootvue.core.service.ActionMapperService;
import com.bootvue.core.service.RoleMenuActionMapperService;
import com.bootvue.core.util.AppContextHolder;
import com.bootvue.core.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import io.jsonwebtoken.Claims;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@WebFilter
@Component
public class AppFilter implements Filter {
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private final AppConfig appConfig;
    private final ActionMapperService actionMapperService;
    private final RoleMenuActionMapperService roleMenuActionMapperService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        XssHttpServletRequestWrapper requestWrapper = new XssHttpServletRequestWrapper(httpServletRequest);

        String path = requestWrapper.getRequestURI();

        try {
            // 放行
            if (!CollectionUtils.isEmpty(appConfig.getSkipUrls())) {
                for (String skipUrl : appConfig.getSkipUrls()) {
                    if (PATH_MATCHER.match(skipUrl, path)) {
                        if (path.startsWith("/auth")) {
                            // 检查客户端Key
                            String appid = requestWrapper.getParameter("appid");
                            String secret = requestWrapper.getParameter("secret");
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

            String token = requestWrapper.getHeader("token");
            if (!StringUtils.hasText(token) || !JwtUtil.isVerify(token)) {
                throw new AppException(RCode.UNAUTHORIZED_ERROR);
            }

            Claims claims = JwtUtil.decode(token);

            UserInfo userInfo = new UserInfo(claims.get("user_id", Long.class),
                    claims.get("role_id", Long.class),
                    claims.get("username", String.class));

            AppContextHolder.setUserInfo(userInfo);

            // api接口权限校验 /admin /xxx的需要权限校验
            handleRequestAuthorization(path, userInfo);
            chain.doFilter(requestWrapper, response);
        } catch (AppException e) {
            handleResponse(e.getCode(), e.getMsg(), servletResponse);
        } catch (Exception e) {
            handleResponse(RCode.DEFAULT.getCode(), RCode.DEFAULT.getMsg(), servletResponse);
        } finally {
            log.info("AppFilter拦截器处理耗时: {}s", stopwatch.stop().elapsed(TimeUnit.SECONDS));
            AppContextHolder.remove();
        }
    }

    // 权限验证
    private void handleRequestAuthorization(String path, UserInfo userInfo) {
        if (CollectionUtils.isEmpty(appConfig.getAuthorizationUrls())) {
            return;
        }

        boolean flag = false;
        for (String authorizationUrl : appConfig.getAuthorizationUrls()) {
            if (PATH_MATCHER.match(authorizationUrl, path)) {
                Action action = actionMapperService.getAction(path);
                // 用户角色对应的action权限
                List<RoleMenuAction> actions = roleMenuActionMapperService.getRoleMenuActions(userInfo.getRoleId());

                if (ObjectUtils.isEmpty(action) || CollectionUtils.isEmpty(actions)) {
                    break;
                }
                List<String> ids = actions.stream().flatMap(e ->
                        Splitter.on(",").trimResults().omitEmptyStrings().splitToStream(e.getActionIds()))
                        .collect(Collectors.toList());
                if (ids.contains(action.getId())) {
                    flag = true;
                }
                break;
            }
        }
        if (!flag) {
            log.warn("用户: {} id: {} 角色id: {} 访问资源: {} 没有权限 ", userInfo.getUsername(), userInfo.getUserId(), userInfo.getRoleId(), path);
            throw new AppException(RCode.ACCESS_DENY);
        }
    }

    // 响应
    private void handleResponse(Integer code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding(CharsetUtil.UTF_8.toString());
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(R.error(code, msg)));
        writer.flush();
    }

    @Bean
    @Primary
    public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
        xssModule.addSerializer(String.class, new XssStringJsonSerializer());
        objectMapper.registerModule(xssModule);
        return objectMapper;
    }
}

