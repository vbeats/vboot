package com.bootvue.core.config.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cache {
    private String cacheName;  // redis缓存名  实际对应cache: xxx
    private Long ttl;  // 最大生存时间
    private Long maxIdleTime; // 最大空闲时间
}
