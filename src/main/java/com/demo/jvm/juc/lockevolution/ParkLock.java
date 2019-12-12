package com.demo.jvm.juc.lockevolution;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ParkLock {
    /***
     * CAS虽然很高效,但是它也存在ABA问题
     * CAS需要在操作值的时候检查内存值是否发生变化，没有发生变化才会更新内存值。
     * 但是如果内存值原来是A，后来变成了B，然后又变成了A，那么CAS进行检查时会发现值没有发生变化，但是实际上是有变化的。
     * ABA问题的解决思路就是在变量前面添加版本号，每次变量更新的时候都把版本号加一，这样变化过程就从“A－B－A”变成了“1A－2B－3A”。
     *
     * JDK从1.5开始提供了AtomicStampedReference类来解决ABA问题，具体操作封装在compareAndSet()中。
     * compareAndSet()首先检查当前引用和当前标志与预期引用和预期标志是否相等，如果都相等，则以原子方式将引用值和标志的值设置为给定的更新值。
     */
    private static volatile AtomicInteger status = new AtomicInteger(0);
    private static LinkedBlockingQueue<Thread> parkingThreadQueue = new LinkedBlockingQueue();

    public static void lock(){
        if(!status.compareAndSet(0,1)){
            try {
                parkingThreadQueue.put(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
        }
    }

    public static void unlock(){
        status.compareAndSet(1,0);
        for (Thread t : parkingThreadQueue) {
            LockSupport.unpark(t);
        }
    }
}
