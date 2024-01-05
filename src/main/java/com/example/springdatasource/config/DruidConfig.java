package com.example.springdatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.springdatasource.constant.CommonConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 数据源配置类
 */
@Configuration
@MapperScan("com.example.springdatasource.mapper")
public class DruidConfig {

    /**
     * 主库数据源bean，和spring.datasource.druid.master配置绑定
     * @return
     */
    @Bean(name = CommonConstant.MASTER)
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource()
    {
        DruidDataSource master = DruidDataSourceBuilder.create().build();
        return master;
    }

    /**
     * 从库数据源bean，和spring.datasource.druid.slave绑定
     * @return
     */
    @Bean(name = CommonConstant.SLAVE)
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slaveDataSource()
    {
        DruidDataSource slave = DruidDataSourceBuilder.create().build();
        return slave;
    }

    /**
     * 动态数据源bean
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource()
    {
        //创建一个存放数据源的map
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        //将上述两个数据源存放到map中
        dataSourceMap.put(CommonConstant.MASTER,masterDataSource());
        dataSourceMap.put(CommonConstant.SLAVE,slaveDataSource());


        //设置动态数据源，默认为master配置的数据源，并指定数据源的map
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);


        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }
}
