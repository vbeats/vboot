package com.bootvue.core.config.app;

import com.bootvue.core.constant.PlatformType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "vboot")
@Getter
@Setter
@Slf4j
public class AppConfig {

    private boolean swagger;  // 是否开启swagger
    private Set<String> skipUrls; // 直接放行的url
    private Set<Key> authKey; // 客户端密钥等参数
    private Set<Cache> cache; // redis spring cache 缓存

    public static Key getKeys(AppConfig appConfig, PlatformType platform) {
        for (Key key : appConfig.getAuthKey()) {
            if (key.getPlatform().equals(platform.getValue())) {
                return key;
            }
        }
        return null;
    }
}
