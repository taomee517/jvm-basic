package com.demo.jvm.classuse.model;

/**
 * @author TAOMEE
 */
public class ActiveChild extends ActiveParent {
    public static String childStr = "Hello,Child!";
    static {
        System.out.println("child static block");
    }
}
