package com.demo.jvm.classloader;

public class SourceClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        while (classLoader!=null){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
    }
}
