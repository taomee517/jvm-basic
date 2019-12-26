package com.demo.jvm.juc.distributedlock.lock;

import com.demo.jvm.juc.distributedlock.service.MethodLockService;
import lombok.Data;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Data
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
            System.out.println(Thread.currentThread().getName() + "获得锁");
        }else {
            System.out.println(Thread.currentThread().getName() + "等待锁");
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
            System.out.println(Thread.currentThread().getName() + "释放锁");
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
