package com.demo.jvm.classuse.model;


/**
 * @author TAOMEE
 */
public class StaticFinalModel {
    public static final String sfmStr = "Hello,Final String!";
    public static final short s = 7;
    public static final int i = 128;
    public static final int m = 0;
    static {
        System.out.println("StaticFinalModel static block");
    }
}
