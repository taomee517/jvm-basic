package com.demo.jvm.classuse.model;


import java.util.UUID;

/**
 * @author TAOMEE
 */
public class StaticFinalRandomModel {
    public static final String sfrmStr = UUID.randomUUID().toString();
    static {
        System.out.println("StaticFinalRandomModel static block");
    }
}
