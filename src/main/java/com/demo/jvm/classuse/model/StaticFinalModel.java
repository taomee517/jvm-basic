package com.demo.jvm.classuse.model;


/**
 * @author TAOMEE
 */
public class StaticFinalModel {
    public static final String sfmStr = "Hello,Final String!";
    static {
        System.out.println("StaticFinalModel static block");
    }
}
