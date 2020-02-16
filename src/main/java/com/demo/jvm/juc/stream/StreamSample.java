package com.demo.jvm.juc.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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


        //reduce的意思是：根据一定的规则将Stream中的元素进行计算后返回一个唯一的值
        //这里不要用parallel
        int max = list.stream()
                .reduce((a,b) -> a>b?a:b)
                .get();
        System.out.println("reduce计算后的最大值是：" + max);

        int s = list.stream()
                .reduce(0, (total, a) -> total + a);
        System.out.println("reduce计算后的总和是：" + s);


        //orElse()：如果有值则将其返回，否则返回指定的其它值
        int product = IntStream.range(2,8)
                .reduce((a,b) -> a*b)
                .orElse(-1);
        System.out.println("reduce计算后的乘积是：" + product);

        //流式写法, mapToInt(i->i)部分也可以用一个方法来替换
//        int streamSum = list.stream().filter(i->i%2==0).mapToInt(i->i).sum();
        int streamSum = list.stream().filter(i->i%2==0).mapToInt(StreamSample::transfer).sum();
        System.out.println("List偶数流式求和为：" + streamSum);
    }
}
