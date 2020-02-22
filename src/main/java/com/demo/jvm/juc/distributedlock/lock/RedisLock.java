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

/**
 * 分布式锁的三大难点：
 * 1.防止死锁
 *      解决方案：设置超时过期时间
 * 2.非原子性操作
 *      如setNx, expire
 *      解决方案: setNxEx
 * 3. 误删锁
 *      如进程1对方法A加锁，设置超时30秒，进程2获得锁，对方法A进行操作
 *      进程1对方法A进行解锁操作，可能会误删进程2的锁
 *      解决方案：解锁时需要判断是否自己的加的锁
 *
 */

@Data
@Slf4j
public class RedisLock implements Lock {
    private String methodName;
    private BlockingQueue<Thread> parkThreads = new LinkedBlockingQueue<>();
    private static RedisDao redisDao = new RedisDao();

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
        //如果key存在，则该方法已经被加锁
        //如果key不存在，则该
        boolean flag = redisDao.setNxEx(methodName,value);
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
        if(redisDao.delete(methodName)){
            log.info("{}释放锁",Thread.currentThread().getName());
            for(Thread thread : parkThreads){
                LockSupport.unpark(thread);
            }

//            if(parkThreads.size()>0){
//                LockSupport.unpark(parkThreads.poll());
//            }
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
