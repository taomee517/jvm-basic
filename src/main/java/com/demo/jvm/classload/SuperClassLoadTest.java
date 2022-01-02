package com.demo.jvm.classload;

public class SuperClassLoadTest {

    public static void main(String[] args) {
        Son son = new Son();
        son.print();
    }

}

class Father{
    int x = 10;
    public Father(){
        this.print();
        x = 20;
    }

    public void print() {
        System.out.println("Father.x = " + x);
    }
}

class Son extends Father{
    int x = 30;
    public Son() {
        super();
        this.print();
        x = 40;
    }

    public void print() {
        System.out.println("Son.x = " + x);
    }
}