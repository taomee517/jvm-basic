package com.demo.jvm.juc.stream;

import java.util.stream.IntStream;

public class ParallelStreamFindAny {
    public static void main(String[] args) {
        for (int i=0;i<20;i++) {
            int factor = findOne();
            System.out.println(factor);
        }
    }

    //并行时，如果findAny,结果可能不是唯一的
    private static int findOne(){
        int sevenFactor = IntStream.range(1,200)
                .parallel()
                .filter(i-> i%7==0)
                .findFirst()
//                .findAny()
                .orElse(-1);
        return sevenFactor;
    }
}
