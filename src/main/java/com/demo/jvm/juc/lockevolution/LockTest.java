package com.demo.jvm.juc.lockevolution;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    public static void main(String[] args) {
//        ReentrantLock lock = new ReentrantLock();
//        lock.lock();


//        whileLockTest();
//        sleepLockTest();
        parkLockTest();
    }


    public static void whileLockTest(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                WhileLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println("当前线程为：" + threadName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WhileLock.unlock();
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");

        t1.start();
        t2.start();
    }




    public static void sleepLockTest(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                SleepLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println("当前线程为：" + threadName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SleepLock.unlock();
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");

        t1.start();
        t2.start();
    }


    public static void parkLockTest() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ParkLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println("当前线程为：" + threadName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ParkLock.unlock();
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");

        t1.start();
        t2.start();
    }



}
