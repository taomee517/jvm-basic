package com.demo.jvm.juc.stream;

import java.util.Arrays;
import java.util.List;

public class ParallelStreamSample {

    public static void main(String[] args) {
        //对比 stream 与 parallelStream
        List<Integer> list = Arrays.asList(11,25,64);

        //非并行
        list.stream()
            .map(ParallelStreamSample::toHex)
            .forEach(System.out::println);

        System.out.println("====串行/并行====");

        //并行处理
        list.stream()
            .parallel()
            .map(ParallelStreamSample::toHex)
            .forEach(System.out::println);
    }

    private static String toHex(int i){
        return Integer.toHexString(i);
    }
}
