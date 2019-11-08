package com.demo.jvm.classloader;

public class BootstrapAndAppClassLoader {
    public static void main(String[] args) throws Exception{
        Class<?> clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());

        Class<?> cls = Class.forName("com.demo.jvm.classloader.Model");
        System.out.println(cls.getClassLoader());
    }
}
