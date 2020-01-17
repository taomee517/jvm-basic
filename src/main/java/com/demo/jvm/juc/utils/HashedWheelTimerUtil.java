package com.demo.jvm.juc.utils;

import io.netty.util.HashedWheelTimer;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class HashedWheelTimerUtil {

    private HashedWheelTimer timer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
    private static class SingletonHolder {
        public final static HashedWheelTimerUtil instance = new HashedWheelTimerUtil();
    }

    public static HashedWheelTimerUtil instance() {
        return SingletonHolder.instance;
    }

    private HashedWheelTimerUtil(){

    }

}
