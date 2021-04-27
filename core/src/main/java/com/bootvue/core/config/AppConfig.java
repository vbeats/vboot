package com.bootvue.core.config;

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

    private boolean swagger;
    private Set<String> skipUrls;
    private String publicKey;
    private String privateKey;
}
