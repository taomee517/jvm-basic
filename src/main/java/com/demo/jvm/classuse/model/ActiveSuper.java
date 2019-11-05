package com.demo.jvm.classuse.model;

public class ActiveSuper {
    public static int b = 4;

    static {
        System.out.println("ActiveSuper init");
    }

    public static void doSomething(){
        System.out.println("do something");
    }
}
