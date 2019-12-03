package com.demo.jvm.deadlock;

/**
 * 一个死锁案例
 */
public class DeadLockDemo {
    public static final String A = "A";
    public static final String B = "B";


    /**
     * 如何避免死锁的几个常见方法。
     *
     *  避免一个线程同时获取多个锁。
     *  避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源。
     *  尝试使用定时锁，使用tryLock(timeout)来替代使用内部锁机制。
     *  对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败。
     *
     */
    public static void main(String[] args) {
        deadMethod();
    }

    public static void deadMethod(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                synchronized (A){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println(B);
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run(){
                synchronized (B){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A){
                        System.out.println(A);
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
