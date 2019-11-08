package com.demo.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class MyClassLoader1 extends ClassLoader {
    private String classLoaderName;

    private String path;

    public static final String CLASS_SUFFIX = ".class";

    public MyClassLoader1(){

    }

    public MyClassLoader1(String classLoaderName){
        //将系统类加载器作为父加载器
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader1(ClassLoader parent, String classLoaderName){
        //将parent作为父加载器
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        log.info("进入MyClassLoader0.findClass方法, className = {}",className);
        byte[] classData = loadClassData(className);
        return this.defineClass(className,classData,0,classData.length);
    }


    public byte[] loadClassData(String className){
        InputStream is = null;
        byte[] classData = null;
        ByteArrayOutputStream baos = null;

        try {
            this.classLoaderName = this.classLoaderName.replace(".","\\");
            is = new FileInputStream(new File(this.path + className.replace(".","\\") + CLASS_SUFFIX));
            baos = new ByteArrayOutputStream();
            int tempByte;
            while (-1!=(tempByte=is.read())){
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
        Class<?> clazz = classLoader.loadClass("com.demo.jvm.classloader.MyClassLoader0");
        log.info("Class-{} 的类加载器是：{}", clazz.getName(),clazz.getClassLoader());
        Object obj = clazz.newInstance();
        log.info("newInstance结果：{}", obj);
    }

    public static void main(String[] args) throws Exception {
        String classLoaderName = "testLoader";
        MyClassLoader1 classLoader = new MyClassLoader1(classLoaderName);
        classLoader.setPath("C:\\TM Work\\temp\\");
        test(classLoader);
    }
}
