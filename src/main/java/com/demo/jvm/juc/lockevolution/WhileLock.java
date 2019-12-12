package com.demo.jvm.juc.lockevolution;

import java.util.concurrent.atomic.AtomicInteger;

public class WhileLock {
    private static volatile AtomicInteger status = new AtomicInteger(0);

    /**
     * 没有获得锁资源的线程会处于无限while循环当中，
     * 虽然起到了上锁的作用，但是CPU会飙高
     */
    public static void lock(){
        while (!status.compareAndSet(0,1)){
        }
    }

    public static void unlock(){
        status.compareAndSet(1,0);
    }
}
