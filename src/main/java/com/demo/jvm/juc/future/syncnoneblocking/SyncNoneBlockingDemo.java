package com.demo.jvm.juc.future.syncnoneblocking;

import com.demo.jvm.juc.future.common.FutureCommonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author taomee517@qq.com
 * @Desc 同步轻阻塞案例
 *       应该是没有这种实现的，如果真要这种方式，只能从减少syncCalculate方法的执行时间着手
 */
@Slf4j
public class SyncNoneBlockingDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = syncCalculate();
        long end = System.currentTimeMillis();
        long offset = end - start;
        log.info("同步轻阻塞方式,执行结果: result = {}, 耗时： offset = {}",result, offset);
    }

    /**
     * 通常要花3秒才能完成的操作，
     * 经过优化后1秒就完成了
     * 也减少了阻塞的时间，提高了程序的效率
     */
    static int syncCalculate(){
        log.info("线程：thread = {} 开始执行耗时运算", Thread.currentThread().getName());
        FutureCommonUtil.timeConsume(1000);
        return FutureCommonUtil.COMMON_RESULT;
    }
}
