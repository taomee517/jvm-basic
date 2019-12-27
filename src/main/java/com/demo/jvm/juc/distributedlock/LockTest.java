package com.demo.jvm.juc.distributedlock;

import com.demo.jvm.juc.distributedlock.lock.MysqlLock;
import com.demo.jvm.juc.distributedlock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockTest {

    public static void main(String[] args) throws Exception{
        /**
         * 分布式锁的实现：
         *  1.基于数据库的锁：
         *      a. 乐观锁-基于version实现（读取频繁使用乐观锁，写入频繁使用悲观锁）
         *      b. 悲观锁-基于数据库级别的for update
         *  2. 基于redis原子操作实现的锁-基于setNx,expire实现
         *  3. 基于zookeeper的锁-基于InterProceessMutex实现
         *  4. 基于redisson的分布式锁
         *
         */


//        MysqlLock lock = new MysqlLock("demo");
        RedisLock lock = new RedisLock("demo");

        for(int i=0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("{}执行任务!",Thread.currentThread().getName());
                    lock.unlock();
                }
            }).start();
        }

    }
}
