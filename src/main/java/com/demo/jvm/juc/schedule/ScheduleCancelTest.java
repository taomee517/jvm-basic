package com.demo.jvm.juc.schedule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleCancelTest {

    public static void main(String[] args) {
        ScheduleTaskExecutor executor = new ScheduleTaskExecutor();
        executor.scheduleStart();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.scheduleCancel();

    }




}


