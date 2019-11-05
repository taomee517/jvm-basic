package com.demo.jvm.classloader;

public class ClassLoaderGetTest {
    public static void main(String[] args) throws Exception {
        Class<?> clazz=Class.forName("com.demo.jvm.classloader.ClassLoaderGetTest");
        //获得当前类的ClassLoader
        ClassLoader classLoader1 = clazz.getClassLoader();
        //获得当前线程上下文的ClassLoader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //获得系统的ClassLoader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        System.out.println(classLoader1);
        System.out.println(contextClassLoader);
        System.out.println(systemClassLoader);
    }
}
