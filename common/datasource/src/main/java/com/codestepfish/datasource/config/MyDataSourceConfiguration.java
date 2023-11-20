package com.codestepfish.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyDataSourceConfiguration {
    private final DynamicDataSourceProperties properties;
    private final DefaultDataSourceCreator dataSourceCreator;
    private final DataSource shardingSphereDataSource;

    public MyDataSourceConfiguration(DynamicDataSourceProperties properties, DefaultDataSourceCreator dataSourceCreator, @Qualifier("shardingSphereDataSource") DataSource shardingSphereDataSource) {
        this.properties = properties;
        this.dataSourceCreator = dataSourceCreator;
        this.shardingSphereDataSource = shardingSphereDataSource;
    }

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new AbstractDataSourceProvider(dataSourceCreator) {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSourceMap = new HashMap<>();
                //把 shardingSphereDataSource 加入多数据源，到时候使用的时候就可以 `@DS("sharding")`
                dataSourceMap.put("sharding", shardingSphereDataSource);
                return dataSourceMap;
            }
        };
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(Collections.singletonList(dynamicDataSourceProvider()));
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setP6spy(properties.getP6spy());
        dataSource.setSeata(properties.getSeata());
        return dataSource;
    }
}
