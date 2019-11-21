package com.demo.jvm.jvmparam;

public class MinorGC {
    /**
     * JVM参数：-Xmx20m -Xms20m -Xmn1m  -XX:+PrintGCDetails
     * 结果：JDK不同版本运行结果有差异
     * @param args
     */
    public static void main(String[] args) {
        byte[] b=null;
        for(int i=0;i<10;i++) {
            b = new byte[2 * 1024 * 1024];
        }
    }

}
