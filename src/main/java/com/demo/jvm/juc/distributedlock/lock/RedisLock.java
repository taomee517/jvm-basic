package com.demo.jvm.juc.distributedlock.lock;

import com.demo.jvm.juc.distributedlock.service.RedisDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Data
@Slf4j
public class RedisLock implements Lock {
    private String methodName;
    private BlockingQueue<Thread> parkThreads = new LinkedBlockingQueue<>();

    public RedisLock(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public void lock() {
        while (true){
            if (!tryLock()) {
                parkThreads.add(Thread.currentThread());
                LockSupport.park();
            }else {
                break;
            }
        }
    }


    @Override
    public boolean tryLock() {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getId();
        boolean flag = RedisDao.setNxEx(methodName,value);
        if(flag){
            log.info("{}获得锁",Thread.currentThread().getName());
        }else {
            log.info("{}等待锁",Thread.currentThread().getName());
        }
        return flag;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if(RedisDao.delete(methodName)){
            log.info("{}释放锁",Thread.currentThread().getName());
            for(Thread thread : parkThreads){
                LockSupport.unpark(thread);
            }
        }else {
            log.info("{}释放锁失败",Thread.currentThread().getName());
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }
}
