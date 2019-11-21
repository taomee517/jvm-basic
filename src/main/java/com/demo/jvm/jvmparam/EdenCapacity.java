package com.demo.jvm.jvmparam;

public class EdenCapacity {
    /**
     * 使用 JVM 参数-XX:+PrintGCDetails -Xmx20M -Xms20M.查看运行状态
     * @param args
     */
    public static void main(String[] args) {
        byte[] b1,b2,b3;
        for (int i=0;i<100;i++) {
//            //定义变量
//            byte[] b1,b2,b3,b4;
//            //分配 1MB 堆空间，考察堆空间的使用情况
//            b1=new byte[1024*1024];
//            b2=new byte[1024*1024];
//            b3=new byte[1024*1024];
//            b4=new byte[1024*1024];


            //分配 0.5MB 堆空间
            b1=new byte[1024*512];
            //分配 4MB 堆空间
            b2=new byte[1024*1024*4];
            b3=new byte[1024*1024*4];
            //使 b3 可以被回收
            b3=null;
            //分配 4MB 堆空间
            b3=new byte[1024*1024*4];
        }
    }
}
