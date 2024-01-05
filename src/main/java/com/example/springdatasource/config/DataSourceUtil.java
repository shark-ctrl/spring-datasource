package com.example.springdatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.springdatasource.dto.DataSourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 数据源管理工具类
 */
@Slf4j
@Component
public class DataSourceUtil {

    @Resource
    DynamicDataSource dynamicDataSource;

    /**
     * 测试数据源是否可用，如果可用即直接返回
     * @param dataSourceInfo
     * @return
     * @throws SQLException
     */
    public DruidDataSource createDataSourceConnection(DataSourceInfo dataSourceInfo) throws SQLException {
        //创建数据源对象
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceInfo.getUrl());
        druidDataSource.setUsername(dataSourceInfo.getUserName());
        druidDataSource.setPassword(dataSourceInfo.getPassWord());
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setConnectionErrorRetryAttempts(0);
        try {
            //尝试连接数据源
            druidDataSource.getConnection(2000);
            log.info("数据源:{}连接成功", dataSourceInfo);
            return druidDataSource;
        } catch (SQLException e) {
            log.error("数据源 {} 连接失败,用户名：{}，密码 {}",dataSourceInfo.getUrl(),dataSourceInfo.getUserName(),dataSourceInfo.getPassWord());
            return null;
        }
    }

    /**
     * 将外部数据源存到dynamicDataSource并调用afterPropertiesSet刷新
     * @param druidDataSource
     * @param dataSourceName
     */
    public void addDefineDynamicDataSource(DruidDataSource druidDataSource, String dataSourceName){
        Map<Object, Object> defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
        //存到defineTargetDataSources这个map中
        defineTargetDataSources.put(dataSourceName, druidDataSource);
        dynamicDataSource.setTargetDataSources(defineTargetDataSources);
        //调用afterPropertiesSet刷新这个key
        dynamicDataSource.afterPropertiesSet();
    }
}
