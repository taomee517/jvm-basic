package com.demo.jvm.juc.future.asyncblocking;


import com.demo.jvm.juc.future.common.FutureCommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author taomee517@qq.com
 * @desc 异步阻塞案例
 */
@Slf4j
public class AsyncBlockingDemo {
    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        //JDK自带的Future
        Future<Integer> futureResult = FutureCommonUtil.JDK_POOL.submit(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                return FutureCommonUtil.syncCalculate();
            }
        });
        //JDK自带future,它的get方法是一个阻塞的方法
        //当任务没有执行完成时，它会阻塞,直到超时
        int result = futureResult.get();
        long end = System.currentTimeMillis();
        long offset = end - start;
        log.info("异步阻塞方式,执行结果: result = {}, 耗时： offset = {}", result, offset);
    }
}
