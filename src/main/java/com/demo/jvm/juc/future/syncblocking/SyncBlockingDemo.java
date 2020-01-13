package com.demo.jvm.juc.future.syncblocking;

import com.demo.jvm.juc.future.common.FutureCommonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author taomee517@qq.com
 * @Desc 同步阻塞案例
 */
@Slf4j
public class SyncBlockingDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = FutureCommonUtil.syncCalculate();
        long end = System.currentTimeMillis();
        long offset = end - start;
        log.info("同步且阻塞方式,执行结果: result = {}, 耗时： offset = {}", result, offset);
    }
}
