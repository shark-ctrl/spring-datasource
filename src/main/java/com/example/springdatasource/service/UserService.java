package com.example.springdatasource.service;

import com.example.springdatasource.dto.User;
import com.example.springdatasource.mapper.CommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
public class UserService {
    @Resource
    private CommonMapper commonMapper;


//    public void createUser(User user) {
//
//        this.saveUser(user);
//        if (user.getName().contains("test")) {
//            throw new RuntimeException("姓名不合法");
//        }
//
//    }

    /**
     * spring 通过动态代理方式实现aop，私有方法无法被代理需要改为public才可以
     * @param user
     */
//    @Transactional
//    private void saveUser(User user) {
//        commonMapper.saveUser(user);
//    }
//

    /**
     * spring通过aop实现类的增强，要调用增强的方法必须调用代理对象，this为当前对象
     *
     * @param user
     */
//    @Transactional
//    public void saveUser(User user) {
//        commonMapper.saveUser(user);
//    }
//


//    public void createUser(User user) {
//
//        self.saveUser(user);
//        if (user.getName().contains("test")) {
//            throw new RuntimeException("姓名不合法");
//        }
//
//    }
//    private UserService self;
//    @Transactional
//    public void saveUser(User user) {
//        commonMapper.saveUser(user);
//    }
//    @Transactional
//    public void createUser(User user) {
//        try {
//            commonMapper.saveUser(user);
//            log.info("任意报错" + (1 / 0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    //正确做法
//    @Transactional
//    public void createUser(User user) {
//
//            commonMapper.saveUser(user);
//        log.info("任意报错" + (1 / 0));
//
//
//    }

    ///正确做法
    @Transactional
    public void createUser(User user) {
        try {
            commonMapper.saveUser(user);
            log.info("任意报错" + (1 / 0));
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }


    }


    //默认不处理这种受检异常
//    @Transactional
//    public void createUser(User user) throws IOException {
//
//        commonMapper.saveUser(user);
//       if (1==1){
//           throw new IOException();
//       }
//
//
//    }

//    //上述正确做法
//    @Transactional(rollbackFor = Exception.class)
//    public void createUser(User user) throws IOException {
//
//        commonMapper.saveUser(user);
//       if (1==1){
//           throw new IOException();
//       }
//
//
//    }

}
