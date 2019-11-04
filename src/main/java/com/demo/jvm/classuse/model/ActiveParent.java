package com.demo.jvm.classuse.model;

/**
 * @author TAOMEE
 */
public class ActiveParent{
    public static String parentStr = "Hello,Parent!";
    static {
        System.out.println("parent static block");
    }
}
