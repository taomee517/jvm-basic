package com.demo.jvm.juc.future.asyncnoneblocking;


import com.demo.jvm.juc.future.common.FutureCommonUtil;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author taomee517@qq.com
 * @desc 异步非阻塞案例-Guava实现
 */
@Slf4j
public class AsyncNoneBlockingGuavaDemo {
    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        ListeningExecutorService service = FutureCommonUtil.GUAVA_LISTENING_POOL;
        //Guava封装后的Future,它可以添加callback
        ListenableFuture<Integer> futureResult = service.submit(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                return FutureCommonUtil.syncCalculate();
            }
        });
        Integer result = 0;
        //Guava封装后的Future,它可以添加callback
        //当业务逻辑执行成功，或者失败时会回调FutureCallback的onSuccess或onFailure方法，
        //届时再执行一些获得结果后或执行出错后的操作
        Futures.addCallback(futureResult, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                int result = integer;
                long end = System.currentTimeMillis();
                long offset = end - start;
                log.info("异步非阻塞方式,业务线程执行结果: result = {}, 耗时： offset = {}", result, offset);
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("异步非阻塞方式,业务线程执行出错: cause = {}", throwable);
            }
        });

        long end = System.currentTimeMillis();
        long offset = end - start;
        log.info("异步非阻塞方式,主线程未被阻塞 thread = {}, 到此结果为空：result = {} 耗时： offset = {}", Thread.currentThread().getName(), result, offset);
    }
}
