package com.demo.jvm.juc.distributedlock;

import com.demo.jvm.juc.distributedlock.lock.MysqlLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockTest {

    public static void main(String[] args) throws Exception{
        MysqlLock lock = new MysqlLock("demo");
        for(int i=0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
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
