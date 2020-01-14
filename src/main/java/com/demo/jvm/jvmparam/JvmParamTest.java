package com.demo.jvm.jvmparam;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class JvmParamTest {

    /**
     * 垃圾回收器分类及重要参数配置：
     *
     * 1. 使用-XX:+UseSerialGC 参数可以指定使用新生代串行收集器和老年代串行收集器
     *    当 JVM 在 Client 模式下运行时，它是默认的垃圾收集器
     *      a. -XX:+SurvivorRatio:设置eden区大小和survivor区大小的比例。
     *      b. -XX:+PretenureSizeThreshold:设置大对象直接进入老年代的阈值
     *      c. -XX:MaxTenuringThreshold:设置对象进入老年代的年龄的最大值
     *      d. -XX:NewRatio=4 设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。设置为4，则年轻代与年老代所占比值为1：4，年轻代占整个堆栈的1/5
     *      e. -XX:TargetSurvivorRatio=90 可以提高 from 区的利用率，使 from 区使用到 90%时，再将对象送入年老代
     * 2. 使用-XX:+UseParNewGC 参数设置，表示新生代使用并行收集器，老年代使用串行收集器
     * 3. 使用-XX:+UseParallelOldGC 可以在新生代和老生代都使用并行回收收集器，这是一对非常关注吞吐量的垃圾收集器组合
     * 4. 使用-XX:+UseParallelGC 参数设置，表示新生代和老年代均使用并行回收收集器
     *      a. -XX:+MaxGCPauseMills:设置最大垃圾收集停顿时间
     *      b. -XX:+GCTimeRatio：设置吞吐量大小，它的值是一个 0-100 之间的整数
     *      c. -XX:+UseAdaptiveSizePolicy 可以打开自适应 GC 策略
     *      d. 并行收集器工作时的线程数量可以使用-XX:ParallelGCThreads 参数指定
     *      e. 在默认情况下，当 CPU 数量小于 8 个，ParallelGCThreads 的值等于 CPU 数量，大于 8 个，ParallelGCThreads 的值等于 3+[5*CPU_Count]/8]
     * 5. 设置参数-XX:+UseConcMarkSweepGC 可以要求新生代使用并行收集器，老年代使用 CMS     *
     *      a. 可以通过-XX:ParallelCMSThreads 参数手工设定 CMS 的线程数量,默认线程数是 (ParallelGCThreads+3)/4),ParallelGCThreads 是新生代并行收集器的线程数
     *      b. 由于 CMS 收集器不是独占式的回收器，在 CMS 回收过程中，应用程序仍然在不停地工作。在应用程序工作过程中，又会不断地产生垃圾。这些新生成的垃圾在当前 CMS 回收过程中是无法清除的。
     *         同时，因为应用程序没有中断，所以在 CMS 回收过程中，还应该确保应用程序有足够的内存可用。因此，CMS 收集器不会等待堆内存饱和时才进行垃圾回收，而是当前堆内存使用率达到某一阈值时，
     *         便开始进行回收，以确保应用程序在 CMS 工作过程中依然有足够的空间支持应用程序运行
     *      c. 回收阈值可以使用-XX:CMSInitiatingOccupancyFraction 来指定，默认是 68
     *      d. 在 CMS 的执行过程中，已经出现了内存不足的情况，此时，CMS 回收将会失败，JVM 将启动老年代串行收集器进行垃圾回收.如果这样，应用程序将完全中断，直到垃圾收集完成;
     *      新生代并行回收收集器可以使用以下参数启用：
     *      e. -XX:+CMSClassUnloadingEnabled:允许对类元数据进行回收。
     *      f. -XX:+CMSParallelRemarkEndable:启用并行重标记。
     *      g. -XX:CMSInitatingPermOccupancyFraction:当永久区占用率达到这一百分比后，启动 CMS 回收 (前提是-XX:+CMSClassUnloadingEnabled 激活了)。
     *      h. -XX:UseCMSInitatingOccupancyOnly:表示只在到达阈值的时候，才进行 CMS 回收。
     *      i. -XX:+CMSIncrementalMode:使用增量模式，比较适合单 CPU。
     * 6. -XX:+UseCMSCompactAtFullCollection 参数可以使 CMS 在垃圾收集完成后，进行一次内存碎片整理。内存碎片的整理并不是并发进行的。
     *      a. -XX:CMSFullGCsBeforeCompaction 参数可以用于设定进行多少次 CMS 回收后，进行一次内存压缩。
     * 7. -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC 可以启用 G1 回收器，
     *      a. -XX:MaxGCPauseMillis=20 -XX:GCPauseIntervalMillis=200  用于设置 G1 回收器的目标停顿时间
     *
     * -XX:+PrintGCTimeStamps 打印GC发生的时间戳
     * -XX:+PrintGCDetails 打印出GC详情
     * -XX:+PrintHeapAtGC 每次一次GC后，都打印堆信息
     * -Xloggc:../../gc.log 指定GC日志的存放位置
     *
     * -Xms 是指设定程序启动时占用内存大小。一般来讲，大点，程序会启动的快一点，但是也可能会导致机器时间变慢
     * -Xmx 是指设定程序运行期间最大可占用的内存大小。如果程序运行需要占用更多的内存，超出了这个设置值，就会抛出OutOfMemory异常
     * -Xss 是指设定每个线程的堆栈大小。这个就要依据你的程序，看一个线程大约需要占用多少内存，可能会有多少线程同时运行等。
     * -Xmn 设置年轻代大小。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小
     *
     * 当发生OOM时，直接干掉该程序
     * -XX:OnOutOfMemoryError=kill -9 %p -XX:+UseConcMarkSweepGC
     */

    static HashMap map = new HashMap();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            if(map.size()*512/1024/1024>=400){
                map.clear();
                System.out.println("clean map");
            }
            byte[] b1;
            for(int j=0;j<100;j++){
                b1 = new byte[512];
                //不断消耗内存
                map.put(System.nanoTime(), b1);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Calculate Time: " + (end-start) + " Millis");
    }

    /**
     * OpenJDK官方文档：
     *
     * DefNew + CMS       : -XX:-UseParNewGC -XX:+UseConcMarkSweepGC
     * ParNew + SerialOld : -XX:+UseParNewGC
     * ParNew + iCMS      : -Xincgc
     * ParNew + iCMS      : -XX:+CMSIncrementalMode -XX:+UseConcMarkSweepGC
     * DefNew + iCMS      : -XX:+CMSIncrementalMode -XX:+UseConcMarkSweepGC -XX:-UseParNewGC
     *
     * JDK8后，以下的GC回收器已经过时
     * CMS foreground     : -XX:+UseCMSCompactAtFullCollection
     * CMS foreground     : -XX:+CMSFullGCsBeforeCompaction
     * CMS foreground     : -XX:+UseCMSCollectionPassing
     */

}
