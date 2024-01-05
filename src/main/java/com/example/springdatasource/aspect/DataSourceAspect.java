package com.example.springdatasource.aspect;

import com.example.springdatasource.annotation.Ds;
import com.example.springdatasource.config.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class DataSourceAspect {

    // 设置Ds注解的切点表达式，所有Ds都会触发当前环绕通知
    @Pointcut("@annotation(com.example.springdatasource.annotation.Ds)")
    public void dynamicDataSourcePointCut(){

    }

    //环绕通知
    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取数据源的key
        String key = getDefineAnnotation(joinPoint).value();
        //将数据源设置为该key的数据源
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            //使用完成后切回master
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }

    /**
     * 先判断方法的注解，后判断类的注解，以方法的注解为准
     * @param joinPoint
     * @return
     */
    private Ds getDefineAnnotation(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Ds dataSourceAnnotation = methodSignature.getMethod().getAnnotation(Ds.class);
        if (Objects.nonNull(methodSignature)) {
            return dataSourceAnnotation;
        } else {
            Class<?> dsClass = joinPoint.getTarget().getClass();
            return dsClass.getAnnotation(Ds.class);
        }
    }

}
