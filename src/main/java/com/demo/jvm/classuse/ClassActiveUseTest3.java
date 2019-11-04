package com.demo.jvm.classuse;

import com.demo.jvm.classuse.model.ActiveChild;

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
 * @attention 本案例中涉及到第2,第6 和第7种情况,
 * test static block的输出, 是因为ClassActiveUse3是被Java虚拟机启动时标记为启动类的类，所以它会被加载并被初始化
 */

public class ClassActiveUseTest3 {
    static{
        System.out.println("test static block");
    }

    public static void main(String[] args) {
        System.out.println(ActiveChild.parentStr);
    }
}




