package com.demo.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class MyClassLoader extends ClassLoader {
    private String classLoaderName;

    public static final String CLASS_SUFFIX = ".class";

    public MyClassLoader(){

    }

    public MyClassLoader(String classLoaderName){
        //将系统类加载器作为父加载器
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(ClassLoader parent,String classLoaderName){
        //将parent作为父加载器
        super(parent);
        this.classLoaderName = classLoaderName;
    }


    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] classData = loadClassData(className);
        return this.defineClass(className,classData,0,classData.length);
    }


    public byte[] loadClassData(String className){
        InputStream is = null;
        byte[] classData = null;
        ByteArrayOutputStream baos = null;

        try {
            this.classLoaderName = this.classLoaderName.replace(".","/");
            is = new FileInputStream(new File(className + CLASS_SUFFIX));
            baos = new ByteArrayOutputStream();
            int tempByte = is.read();
            while (tempByte!=-1){
                baos.write(tempByte);
            }
            classData = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classData;
    }

    public static void test(ClassLoader classLoader) throws Exception{
        Class<?> clazz = classLoader.loadClass("com.demo.jvm.classloader.MyClassLoader");
        log.info("Class-{} 的类加载器是：{}", clazz.getName(),clazz.getClassLoader());
        Object obj = clazz.newInstance();
        log.info("newInstance结果：{}", obj);
    }

    public static void main(String[] args) throws Exception {
        String classLoaderName = "testLoader";
        MyClassLoader classLoader = new MyClassLoader(classLoaderName);
        test(classLoader);
    }
}
