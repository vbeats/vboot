package com.codestepfish.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "vboot")
@Getter
@Setter
@Slf4j
@ToString
public class AppConfig {

    private Boolean swagger = false;
    private List<String> skipUrls; //  白名单 无需认证 放行uri

}
