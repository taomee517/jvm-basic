package com.demo.jvm.juc.distributedlock.lock;

import com.demo.jvm.juc.distributedlock.service.MethodLockService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Data
@Slf4j
public class MysqlLock implements Lock {
    private MethodLockService lockService = new MethodLockService();
    private String methodName;
    private LinkedList<Thread> parkThreads = new LinkedList<>();

    public MysqlLock(String methodName) {
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
        boolean flag = lockService.lockMethod(methodName);
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
        if(lockService.deleteLock(methodName)){
            log.info("{}释放锁",Thread.currentThread().getName());
            for(Thread thread : parkThreads){
                LockSupport.unpark(thread);
            }
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
