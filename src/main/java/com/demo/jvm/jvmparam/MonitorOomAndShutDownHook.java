package com.demo.jvm.jvmparam;

public class MonitorOomAndShutDownHook {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Execute Hook.....");
            }
        });
        thread.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
