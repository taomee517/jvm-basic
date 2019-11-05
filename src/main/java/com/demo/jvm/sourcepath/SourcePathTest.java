package com.demo.jvm.sourcepath;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class SourcePathTest {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);

        String resourceName = "com/demo/jvm/sourcepath/SourcePathTest.class";
        Enumeration<URL> urls = classLoader.getResources(resourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
    }
}
