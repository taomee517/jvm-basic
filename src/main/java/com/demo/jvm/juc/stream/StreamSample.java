package com.demo.jvm.juc.stream;

import java.util.Arrays;
import java.util.List;

public class StreamSample {
    private static int transfer(int i){
        return i;
    }

    public static void main(String[] args) {
        //目的：求list中偶数的和
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);

        //传统的写法
        int sum = 0;
        for(Integer i : list){
            if(i%2==0){
                sum += i;
            }
        }
        System.out.println("List偶数求和为：" + sum);


        //流式写法, mapToInt(i->i)部分也可以用一个方法来替换
//        int streamSum = list.stream().filter(i->i%2==0).mapToInt(i->i).sum();
        int streamSum = list.stream().filter(i->i%2==0).mapToInt(StreamSample::transfer).sum();
        System.out.println("List偶数流式求和为：" + streamSum);
    }
}
