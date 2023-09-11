package com.codestepfish.web.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.core.config.AppConfig;
import com.codestepfish.core.constant.AppConstants;
import com.codestepfish.core.exception.AppException;
import com.codestepfish.core.model.RCode;
import com.codestepfish.core.serializer.BigNumberSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfig implements WebMvcConfigurer {

    private final AppConfig appConfig;

    /**
     * 重写json消息转化器 response过滤掉空字段
     *
     * @param converters 消息转化器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule();

        // 过滤 空 字段
        simpleModule.addDeserializer(String.class, new StdDeserializer<>(String.class) {
            @Override
            public String deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
                return StringUtils.hasText(p.getText()) ? p.getText().trim() : null;
            }
        });

        // js 长数字处理
        simpleModule.addSerializer(Long.class, BigNumberSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, BigNumberSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, BigNumberSerializer.instance);

        mapper.registerModule(simpleModule);

        converter.setObjectMapper(mapper);
        converters.add(converter);
    }

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/**")
                            .notMatch(appConfig.getSkipUrls())
                            .check(StpUtil::checkLogin)
                            .check(() -> {
                                // 请求头 clientId 与 token 里的是否一致
                                String clientId = String.valueOf(StpUtil.getExtra(AppConstants.CLIENT_ID));
                                String headerClientId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AppConstants.CLIENT_ID);
                                if (!clientId.equalsIgnoreCase(headerClientId)) {
                                    throw new AppException(RCode.UNAUTHORIZED_ERROR.getCode(), "client不匹配");
                                }
                            })
                    ;
                }))
                .addPathPatterns("/**")
                .excludePathPatterns(appConfig.getSkipUrls());
    }
}
