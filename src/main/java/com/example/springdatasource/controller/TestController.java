package com.example.springdatasource.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.springdatasource.config.DataSourceUtil;
import com.example.springdatasource.config.DynamicDataSourceHolder;
import com.example.springdatasource.constant.CommonConstant;
import com.example.springdatasource.dto.Car;
import com.example.springdatasource.dto.DataSourceInfo;
import com.example.springdatasource.dto.User;
import com.example.springdatasource.mapper.CommonMapper;
import com.example.springdatasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
public class TestController {

    @Resource
    private DataSourceUtil dataSourceUtil;
    @Resource
    private CommonMapper commonMapper;
    @Autowired
    private UserService userService;


    @PostMapping("/orderCar")
    public boolean dynamicDataSourceTest(@RequestBody Map<String,Object> params) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        //在主库中查询汽车信息列表
        User user = commonMapper.getUserInfo((String) params.get("uid"));
        if (user==null){
            throw new RuntimeException("用户不存在");
        }


        //在从库中查询db3数据源信息
        DynamicDataSourceHolder.setDynamicDataSourceKey(CommonConstant.SLAVE);
        DataSourceInfo dataSourceInfo = commonMapper.getNewDataSourceInfo("slave2");
        map.put("dataSource", dataSourceInfo);
        log.info("数据源信息：{}", dataSourceInfo);
        //尝试db3的连接是否可用
        DruidDataSource druidDataSource = dataSourceUtil.createDataSourceConnection(dataSourceInfo);


        Car car=null;

        if (Objects.nonNull(druidDataSource)) {
            //如果db3可用则直接将db3存到动态数据源map中
            dataSourceUtil.addDefineDynamicDataSource(druidDataSource, dataSourceInfo.getDataSourceKey());
            //切换当前数据源为db3
            DynamicDataSourceHolder.setDynamicDataSourceKey(dataSourceInfo.getDataSourceKey());
            //在新的数据源中查询用户信息
             car = commonMapper.getCarInfo((String) params.get("cid"));
            if (car==null){
                throw new RuntimeException("汽车不存在");
            }
        }

        //切回数据源源2
        DynamicDataSourceHolder.setDynamicDataSourceKey(CommonConstant.SLAVE);
        Map<String,Object> orderInfo=new HashMap<>();
        orderInfo.put("uid",user.getId());
        orderInfo.put("cid",car.getId());
        orderInfo.put("total",car.getPrice());
        commonMapper.saveOrderInfo(orderInfo);

        return true;
    }



    @PostMapping("/orderCar2")
    public boolean orderCar2(@RequestBody Map<String,Object> params) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        //在主库中查询汽车信息列表
        User user = commonMapper.getUserInfo((String) params.get("uid"));
        if (user==null){
            throw new RuntimeException("用户不存在");
        }


        //在从库中查询db3数据源信息
        DataSourceInfo dataSourceInfo = commonMapper.getNewDataSourceInfo("slave2");
        map.put("dataSource", dataSourceInfo);
        log.info("数据源信息：{}", dataSourceInfo);
        //尝试db3的连接是否可用
        DruidDataSource druidDataSource = dataSourceUtil.createDataSourceConnection(dataSourceInfo);



        Car car=null;

        if (Objects.nonNull(druidDataSource)) {
            dataSourceUtil.addDefineDynamicDataSource(druidDataSource,dataSourceInfo.getDataSourceKey());
            //在新的数据源中查询用户信息
            car = commonMapper.getCarInfo((String) params.get("cid"));
            if (car==null){
                throw new RuntimeException("汽车不存在");
            }
        }

        //切回数据源源2
        Map<String,Object> orderInfo=new HashMap<>();
        orderInfo.put("uid",user.getId());
        orderInfo.put("cid",car.getId());
        orderInfo.put("total",car.getPrice());
        commonMapper.saveOrderInfo(orderInfo);

        return true;
    }

    @PostMapping("saveUser")
    public void saveUser(@RequestBody User user) throws IOException {
        userService.createUser(user);
    }



}
