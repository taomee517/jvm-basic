package com.demo.jvm.juc.stream;

import java.util.Arrays;
import java.util.List;

public class ParallelStreamSample {

    public static void main(String[] args) {
        //对比 stream 与 parallelStream
        List<Integer> list = Arrays.asList(11,25,64);

        //非并行
//        list.stream()
//            .map(ParallelStreamSample::toHex)
//            .forEach(System.out::println);

        System.out.println("====串行/并行====");

        //并行处理 也可以直接写成 list.parallelStream()
        //forEachOrdered 保证按list的顺序处理后的pipeline计算出的结果
        list.stream()
            .parallel()
            .map(ParallelStreamSample::toHex)
            .forEachOrdered(ParallelStreamSample::printIt);


    }

    private static String toHex(int i){
        System.out.println("toHex thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.toHexString(i);
    }

    private static void printIt(String hex){
        System.out.println("printIt:" + hex + ",thread:" + Thread.currentThread().getName());
    }
}
