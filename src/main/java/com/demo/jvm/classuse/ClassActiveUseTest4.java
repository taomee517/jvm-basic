package com.demo.jvm.classuse;

import com.demo.jvm.classuse.model.StaticFinalModel;

/**
 * @author taomee
 * @descption java程序对类的主动使用包含以下7种情况：
 * 1.创建类的实例
 * 2.访问某个类或接口的静态变量，或者对该静态变量赋值（助记符：getstatic,putstatic）
 * 3.调用类的静态方法(助记符：invokestatic)
 * 4.反射（如Class.forName("com.test.Test")）
 * 5.初始化一个类的子类
 * 6.初始化一个类的时候，如果该类有父类，也会对父类进行初始化，如果父类也有父类，也会往上进行初始化
 * 7.Java虚拟机启动时被标记为启动类的类（Java Test）
 *      JDK1.7开始提供的动态语言支持
 *      java.lang.invoke.MethodHandle实例的解析结果 REF_getStatic, REF_putStatic,REF_invokeStatic句柄对应的类没有初始化，则初始化
 *
 * @attention 本案例中不涉及除main方法所在类以外的类的主动使用
 * 常量在编译阶段，会被存入到调用这个常量的方法所在的类的常量池中
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化
 */

public class ClassActiveUseTest4 {
    public static void main(String[] args) {
        System.out.println(StaticFinalModel.sfmStr);
    }
}




