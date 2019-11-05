package com.demo.jvm.classloader;

/**
 * @author TAOMEE
 * @desc 数组的类加载器就是数组元素的类加载器
 *
 */
public class ArrayClassLoaderTest {
    public static void main(String[] args) {
        Class<?> clazz = String.class;
        System.out.println(clazz.getClassLoader());

        String[] strs = new String[1];
        System.out.println(strs.getClass().getClassLoader());

        Model[] models = new Model[1];
        System.out.println(models.getClass().getClassLoader());
    }
}
