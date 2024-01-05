package com.example.springdatasource.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 集成抽象类AbstractRoutingDataSource，实现自定义数据源获取逻辑determineCurrentLookupKey
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {
    //备份所有数据源信息，
    private Map<Object, Object> defineTargetDataSources;

    /**
     * 返回当前线程需要用到的数据源bean
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }
}
