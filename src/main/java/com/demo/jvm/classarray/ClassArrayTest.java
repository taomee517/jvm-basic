package com.demo.jvm.classarray;

import com.demo.jvm.classuse.model.ActiveChild;

/**
 * @author TAOMEE
 * @description
 * 助记符：
 * newarray: 表示创建一个指定的原始类型（如int、float、char等）的数组，并将其引用值压入栈顶
 * anewarray: 表示创建一个引用类型（如类、接口、数组）数组，并将其引用值压入栈顶
 */
public class ClassArrayTest {
    public static void main(String[] args) {
        ActiveChild[] activeChildren = new ActiveChild[1];
        System.out.println(activeChildren.getClass());

        ActiveChild[][] activeChildrenArray = new ActiveChild[1][1];
        System.out.println(activeChildrenArray.getClass());

        System.out.println(activeChildren.getClass().getSuperclass());
        System.out.println(activeChildrenArray.getClass().getSuperclass());

        int[] ints=new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());
    }
}
