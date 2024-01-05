package com.example.springdatasource.config;

import com.example.springdatasource.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * 数据源切换处理类
 *
 */
@Slf4j
public class DynamicDataSourceHolder {
    /**
     * 为每个线程存放当前数据源的ThreadLocal
     */
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    /**
     * 为当前线程切换数据源
     */
    public static void setDynamicDataSourceKey(String key) {
        log.info("数据源切换key：{}", key);
        DYNAMIC_DATASOURCE_KEY.set(key);
    }

    /**
     * 获取动态数据源的名称，默认情况下使用mater数据源
     */
    public static String getDynamicDataSourceKey() {
        String key = DYNAMIC_DATASOURCE_KEY.get();
        if (ObjectUtils.isEmpty(key)) {
            key = CommonConstant.MASTER;
        }
        log.info("获取数据源，key：{}", key);
        return key;
    }

    /**
     * 将ThreadLocal置空，移除当前数据源
     */
    public static void removeDynamicDataSourceKey() {
        log.info("移除数据源：{}", DYNAMIC_DATASOURCE_KEY.get());
        DYNAMIC_DATASOURCE_KEY.remove();
    }
}
