package com.demo.jvm.juc.distributedlock.service;

import com.demo.jvm.juc.distributedlock.entity.MethodLock;
import com.demo.jvm.juc.distributedlock.entity.MethodLockExample;
import com.demo.jvm.juc.distributedlock.entity.MethodLockMapper;
import com.demo.jvm.juc.distributedlock.util.SqlSessionBuilder;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class MethodLockService {
    public static final AtomicInteger atomicInt = new AtomicInteger(0);

    public boolean lockMethod(String methodName){
        SqlSession sqlSession = SqlSessionBuilder.instance().getSqlSession();
        boolean flag = false;
        try {
            // mybatis 内部其实已经解析好了 mapper 和 mapping 对应关系，放在一个map中，这里可以直接获取
            // 如果看源码可以发现userMapper 其实是一个代理类MapperProxy，
            // 通过 sqlSession、mapperInterface、mechodCache三个参数构造的
            // MapperProxyFactory 类中 newInstance(MapperProxy<T> mapperProxy)方法
            MethodLockMapper methodLockMapper = sqlSession.getMapper(MethodLockMapper.class);

            /* insert */
            MethodLock lock = new MethodLock();
            lock.setMethodName(methodName);
            lock.setState(true);
            lock.setUpdateTime(new Date());
            lock.setVersion(atomicInt.incrementAndGet());

            flag = methodLockMapper.insert(lock)>0;
        } finally {
            sqlSession.close();
            return flag;
        }
    }


    public boolean deleteLock(String methodName){
        SqlSession sqlSession = SqlSessionBuilder.instance().getSqlSession();
        boolean flag = false;
        try {
            // mybatis 内部其实已经解析好了 mapper 和 mapping 对应关系，放在一个map中，这里可以直接获取
            // 如果看源码可以发现userMapper 其实是一个代理类MapperProxy，
            // 通过 sqlSession、mapperInterface、mechodCache三个参数构造的
            // MapperProxyFactory 类中 newInstance(MapperProxy<T> mapperProxy)方法
            MethodLockMapper methodLockMapper = sqlSession.getMapper(MethodLockMapper.class);
            MethodLockExample example = new MethodLockExample();
            example.createCriteria().andMethodNameEqualTo(methodName);
            flag = methodLockMapper.deleteByExample(example)>0;
        } finally {
            sqlSession.close();
            return flag;
        }
    }
}
