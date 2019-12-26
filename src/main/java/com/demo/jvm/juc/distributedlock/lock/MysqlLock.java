package com.demo.jvm.juc.distributedlock.lock;

import com.demo.jvm.juc.distributedlock.service.MethodLockService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Mysql实现分布式锁
 * 缺点：
 *
 *     1、这把锁强依赖数据库的可用性，数据库是一个单点，一旦数据库挂掉，会导致业务系统不可用。
 *     2、这把锁没有失效时间，一旦解锁操作失败，就会导致锁记录一直在数据库中，其他线程无法再获得到锁。
 *     3、这把锁只能是非阻塞的，因为数据的insert操作，一旦插入失败就会直接报错。没有获得锁的线程并不会进入排队队列，要想再次获得锁就要再次触发获得锁操作。
 *     4、这把锁是非重入的，同一个线程在没有释放锁之前无法再次获得该锁。因为数据中数据已经存在了。
 *
 * 解决方案：
 *      1、数据库是单点？搞两个数据库，数据之前双向同步。一旦挂掉快速切换到备库上。
 *      2、没有失效时间？只要做一个定时任务，每隔一定时间把数据库中的超时数据清理一遍。
 *      3、非阻塞的？搞一个while循环，直到insert成功再返回成功。
 *      4、非重入的？在数据库表中加个字段，记录当前获得锁的机器的主机信息和线程信息，那么下次再获取锁的时候先查询数据库，如果当前机器的主机信息和线程信息在数据库可以查到的话，直接把锁分配给他就可以了。
 *
 *
 */

@Data
@Slf4j
public class MysqlLock implements Lock {
    private MethodLockService lockService = new MethodLockService();
    private String methodName;
    private BlockingQueue<Thread> parkThreads = new LinkedBlockingQueue<>();

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
