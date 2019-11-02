package com.demo.jvm.classload;

/**
 * 本案例可以深刻理解类的加载过程：
 *
 * 1.加载：查找并加载类的二进制数据
 * 2.连接
 *    1.验证-确保被加载类的正确性
 *    2.准备-为类的静态变量分配内存，并将其初始化为默认值
 *    3.解析-把类中的符号引用转为直接引用
 * 3.初始化：为类的静态变量赋予正确的初始值
 *
 *
 * 案例中counter1，singleton，counter2在类的连接-准备阶段分别被赋予了默认值：0，null, 0
 * 而在初始化阶段counter1先被初始化为0
 * singleton初始化调用了Singleton类的的构造方法，分别将counter1，counter2变为1，1
 * counter2的初始化，又将counter2赋值为0，将1的值覆盖了！
 */

public class ClassLoaderTest {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(Singleton.counter1);
        System.out.println(Singleton.counter2);
    }

}

class Singleton{
    public static int counter1;
//    public static int counter2 = 0;

    private static Singleton singleton = new Singleton();

    private Singleton(){
        counter1++;
        counter2++;
        System.out.println("Singleton构造方法中,counter1 = " + counter1);
        System.out.println("Singleton构造方法中,counter2 = " + counter2);
    }

    public static int counter2 = 0;

    public static Singleton getInstance(){
        return singleton;
    }
}
