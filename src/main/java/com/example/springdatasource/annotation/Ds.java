package com.example.springdatasource.annotation;

import com.example.springdatasource.constant.CommonConstant;

import java.lang.annotation.*;


@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ds
{
    /**
     * 切换数据源名称
     */
    public String value() default CommonConstant.MASTER;
}