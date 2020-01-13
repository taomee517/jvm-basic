package com.demo.jvm.juc.future.common;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author taomee517@qq.com
 * @desc future案例的共同类
 */
@Slf4j
public class FutureCommonUtil {
    /** JDK线程池 */
    public static final ExecutorService JDK_POOL = Executors.newSingleThreadExecutor();

    /** NETTY事件驱动线程池 */
    public static final EventExecutorGroup NETTY_EVENT_POOL = new DefaultEventExecutorGroup(4);

    /** GUAVA封装的可回调线程池 */
    public static final ListeningExecutorService GUAVA_LISTENING_POOL = MoreExecutors.listeningDecorator(JDK_POOL);

    public static final int COMMON_RESULT = 100;

    public static void timeConsume(long miliSeconds){
        try {
            Thread.sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static int syncCalculate(){
        log.info("线程：thread = {} 开始执行耗时运算", Thread.currentThread().getName());
        timeConsume(3000);
        return COMMON_RESULT;
    }

}
