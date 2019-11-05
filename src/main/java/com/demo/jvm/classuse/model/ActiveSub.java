package com.demo.jvm.classuse.model;

public class ActiveSub extends ActiveSuper{
    static int a = 3;

    static {
        System.out.println("ActiveSub init");
    }
}
