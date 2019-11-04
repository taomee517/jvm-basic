package com.demo.jvm.classarray;

/**
 * @author TAOMEE
 * @description
 * 助记符：
 * newarray: 表示创建一个指定的原始类型（如int、float、char等）的数组，并将其引用值压入栈顶
 * anewarray: 表示创建一个引用类型（如类、接口、数组）数组，并将其引用值压入栈顶
 */
public class BaseArrayTest {
    public static void main(String[] args) {
        int[] ints = new int[1];
        System.out.println(ints.getClass());

        short[] showts = new short[1];
        System.out.println(showts.getClass());

        boolean[] booleans = new boolean[1];
        System.out.println(booleans.getClass());

        char[] chars = new char[1];
        System.out.println(chars.getClass());

        byte[] bytes = new byte[1];
        System.out.println(bytes.getClass());

        float[] floats = new float[1];
        System.out.println(floats.getClass());

        double[] doubles = new double[1];
        System.out.println(doubles.getClass());
    }
}
