package com.demo.jvm.juc.future.asyncnoneblocking;


import com.demo.jvm.juc.future.common.FutureCommonUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author taomee517@qq.com
 * @desc 异步非阻塞案例-NETTY实现
 */
@Slf4j
public class AsyncNoneBlockingNettyDemo {
    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        //Netty封装后的Future,它可以实现callback,addListener
        Future<Integer> futureResult = FutureCommonUtil.NETTY_EVENT_POOL.submit(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                return FutureCommonUtil.syncCalculate();
            }
        });
        Integer result = 0;
        //Netty封装后的Future,它可以实现addListener,
        //当业务逻辑执行完并得到结果时，再回调Listener的operationComplete方法，再执行一些获得结果后的操作
        futureResult.addListener(new FutureListener<Integer>(){
            @Override
            public void operationComplete(Future<Integer> integerFuture) throws Exception {
                int result = futureResult.get();
                long end = System.currentTimeMillis();
                long offset = end - start;
                log.info("异步非阻塞方式,业务线程执行结果: result = {}, 耗时： offset = {}", result, offset);
            }
        });

        long end = System.currentTimeMillis();
        long offset = end - start;
        log.info("异步非阻塞方式,主线程未被阻塞 thread = {}, 到此结果为空：result = {} 耗时： offset = {}", Thread.currentThread().getName(), result, offset);
    }
}
