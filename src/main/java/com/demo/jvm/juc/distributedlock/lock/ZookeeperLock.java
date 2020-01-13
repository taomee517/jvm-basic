package com.demo.jvm.juc.distributedlock.lock;

import com.demo.jvm.juc.distributedlock.service.ZkLockService;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZookeeperLock implements Lock {
    private ZkLockService lockService = new ZkLockService();

    @Override
    public void lock() {
        try {
            lockService.creatLockNode();
            lockService.tryAcquireLock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        lockService.releaseLock();
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }
}
