package com.demo.jvm.juc.lockevolution;

import java.util.concurrent.atomic.AtomicInteger;

public class SleepLock {
    private static volatile AtomicInteger status = new AtomicInteger(0);

    public static void lock(){
        /**
         * 没有获得锁资源的线程会进入while循环，并且进入休眠
         * 虽然起到了上锁的作用，也没有占用CPU，但休眠的时长无法确定
         */
        if (!status.compareAndSet(0,1)){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unlock(){
        status.compareAndSet(1,0);
    }
}
