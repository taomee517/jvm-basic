package com.demo.jvm.classcomponent;

import org.openjdk.jol.info.ClassLayout;

/**
 * 类的组成
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\11\27 0027 21:29
 */
public class ClassHeader {
    /**
     * JAVA类由三部分组成:
     *      1. 对象头（占12个字节）
     *          a. MarkWord(64bit)
     *          b. Class MetaSpace Address(32bit)
     *      2. 实例数据
     *      3. 对齐填充字节(不满8的整数倍字节的时候才有)
     *
     * 类的状态：
     *      1. 无状态
     *      2. 偏向锁
     *      3. 轻量锁
     *      4. 重量锁
     *      5. GC标记
     *
     */
    public static void main(String[] args) {
        BaseClass model = new BaseClass();
        System.out.println(model.hashCode());
        //打印出一个对象的组成
        System.out.println(ClassLayout.parseInstance(model).toPrintable());

    }
}
