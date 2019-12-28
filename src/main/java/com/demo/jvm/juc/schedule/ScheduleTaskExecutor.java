package com.demo.jvm.juc.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduleTaskExecutor {

    private static volatile Map<String,Boolean> signalMap = new HashMap<>(8);

    public void scheduleStart(){
        int recycleTime = 10;
        int recycleInterval = 1000;
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        CountDownLatch latch = new CountDownLatch(recycleTime);
        String sessionKey = Thread.currentThread().getName();
        signalMap.put(sessionKey,true);
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean flag = signalMap.get(sessionKey);
                    if (latch.getCount()==0 || !flag) {
                        pool.shutdown();
                    }else {
                        log.info("第{}次下发指令，count = {}", recycleTime-latch.getCount()+1,latch.getCount());
                        latch.countDown();
                    }
                } catch (Exception e) {
                    log.error("批量指令下发发生异常！e = {}", e);
                }
            }
        },0, recycleInterval, TimeUnit.MILLISECONDS);
    }

    public void scheduleCancel(){
        String sessionKey = Thread.currentThread().getName();
        signalMap.put(sessionKey,false);
    }
}
