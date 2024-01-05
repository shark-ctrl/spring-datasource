package com.example.springdatasource.mapper;

import com.example.springdatasource.annotation.Ds;
import com.example.springdatasource.dto.Car;
import com.example.springdatasource.dto.DataSourceInfo;
import com.example.springdatasource.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommonMapper {



    @Ds("master")
    User getUserInfo(String id);

    @Ds("master")
    void saveUser(User user);


    @Ds("slave")
    DataSourceInfo getNewDataSourceInfo(String sourceKey);
    @Ds("slave")
    int saveOrderInfo(Map<String,Object> orderInfo);

    @Ds("slave2")
    Car getCarInfo(String id);
}
