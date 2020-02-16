package com.demo.jvm.juc.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ParallelStreamThreadsCount {
    public static void main(String[] args) throws Exception{
        //并行Stream默认的线程数 = 服务器核心数 - 1
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("服务器核心数： cores = " + cores);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println("ParallelStream默认的最大线程数： threads = " + pool.getParallelism());

        IntStream.range(1,21)
                .parallel()
                .mapToObj(ParallelStreamThreadsCount::toHex)
//                .forEach(System.out::println);
                .forEachOrdered(a->{});

        System.out.println("=================自定义线程数执行===================");
        //理论上： ParallelStream的处理线程 <= 服务器核心数
        //I/O阻塞占比尽可能小， 网课原文 0 < I/O blocking factor < 1
        //线程数越大，不代表运行速度一定越快
        process(IntStream.range(1,21).parallel());
    }


    private static String toHex(int value){
        System.out.println("execute toHex, value: " + value + ",thread:" + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.toHexString(value);
    }


    private static void process(IntStream stream) throws Exception{
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.execute(()->stream.mapToObj(ParallelStreamThreadsCount::toHex).forEach(a -> {}));
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
